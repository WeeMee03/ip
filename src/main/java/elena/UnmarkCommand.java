package elena;

public class UnmarkCommand implements Command {
    private final String input;

    public UnmarkCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ElenaException {
        int index = parseIndex(input, "unmark");
        Task task = tasks.get(index);
        task.markAsNotDone();
        ui.showMessage("OK, I've marked this task as not done yet:\n  " + task);
        storage.save(tasks.getAll());
    }

    private int parseIndex(String input, String command) throws ElenaException {
        try {
            int idx = Integer.parseInt(input.split(" ")[1]) - 1;
            if (idx < 0) throw ElenaException.invalidTaskNumber();
            return idx;
        } catch (NumberFormatException e) {
            throw ElenaException.nonIntegerTaskNumber();
        }
    }

    @Override
    public boolean isExit() { return false; }
}
