package elena;

/**
 * Command to mark a task as done.
 */
public class MarkCommand implements Command {
    private final String input;

    public MarkCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ElenaException {
        int index = parseIndex(input, "mark");
        Task task = tasks.get(index);
        task.markAsDone();
        ui.showMessage("Nice! I've marked this task as done:\n  " + task);
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
