package elena;

/**
 * Command to delete a task by index.
 */
public class DeleteCommand implements Command {
    private final String input;

    public DeleteCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ElenaException {
        int index = parseIndex(input);
        Task removed = tasks.delete(index);
        ui.showMessage("Noted. I've removed this task:\n  " + removed + "\nNow you have " + tasks.size() + " tasks in the list.");
        storage.save(tasks.getAll());
    }

    private int parseIndex(String input) throws ElenaException {
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
