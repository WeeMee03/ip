package elena.commands;

import elena.core.ElenaException;
import elena.core.Storage;
import elena.core.TaskList;
import elena.core.Ui;
import elena.tasks.Task;

/**
 * Command to mark a task as not done.
 */
public class UnmarkCommand implements Command {
    private final String input;

    /**
     * Constructs an UnmarkCommand.
     *
     * @param input user input string
     */
    public UnmarkCommand(String input) {
        assert input != null : "Input should not be null";
        this.input = input;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ElenaException {
        int index = parseIndex(input, "unmark");
        assert index >= 0 && index < tasks.size() : "Index must be within task list bounds";

        Task task = tasks.get(index);
        task.markAsNotDone();
        ui.showMessage("OK, I've marked this task as not done yet:\n  " + task);
        storage.save(tasks.getAll());
    }

    private int parseIndex(String input, String command) throws ElenaException {
        try {
            int idx = Integer.parseInt(input.split(" ")[1]) - 1;
            assert idx >= 0 : "Task number must be positive";
            if (idx < 0) {
                throw ElenaException.invalidTaskNumber();
            }
            return idx;
        } catch (NumberFormatException e) {
            throw ElenaException.nonIntegerTaskNumber();
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
