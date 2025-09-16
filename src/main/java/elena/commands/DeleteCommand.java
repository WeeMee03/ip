package elena.commands;

import elena.core.ElenaException;
import elena.core.Storage;
import elena.core.TaskList;
import elena.core.Ui;
import elena.tasks.Task;

/**
 * Command to delete a task by index.
 */
public class DeleteCommand implements Command {
    private final String input;

    /**
     * Constructs a DeleteCommand with the given user input.
     *
     * @param input full user input string
     */
    public DeleteCommand(String input) {
        assert input != null : "Input should not be null";
        this.input = input;
    }

    /**
     * Executes the delete command by removing the specified task.
     *
     * @param tasks   the task list
     * @param ui      the user interface
     * @param storage the storage for saving tasks
     * @throws ElenaException if task number is invalid
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ElenaException {
        int index = parseIndex(input);
        assert index >= 0 && index < tasks.size() : "Index must be within task list bounds";

        Task removed = tasks.delete(index);
        ui.showMessage("Noted. I've removed this task:\n  "
                + removed + "\nNow you have " + tasks.size() + " tasks in the list.");
        storage.save(tasks.getAll());
    }

    private int parseIndex(String input) throws ElenaException {
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
