package elena.commands;

import elena.core.ElenaException;
import elena.core.Storage;
import elena.core.TaskList;
import elena.core.Ui;
import elena.tasks.Task;

/**
 * Command to mark a task as done.
 */
public class MarkCommand implements Command {
    private final String input;

    /**
     * Constructs a MarkCommand with the given input.
     *
     * @param input User input string.
     */
    public MarkCommand(String input) {
        assert input != null : "Input should not be null";
        this.input = input;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ElenaException {
        int index = parseIndex(input);
        assert index >= 0 && index < tasks.size() : "Index must be within task list bounds";

        Task task = tasks.get(index);
        task.markAsDone();
        ui.showMessage("Nice! I've marked this task as done:\n  " + task);
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
