package elena.commands;

import elena.core.ElenaException;
import elena.core.Storage;
import elena.core.TaskList;
import elena.core.Ui;
import elena.tasks.Todo;

/**
 * Represents a command to add a Todo task.
 */
public class AddTodoCommand implements Command {
    private final String input;

    /**
     * Constructs a new AddTodoCommand.
     *
     * @param input the full user input starting with "todo"
     */
    public AddTodoCommand(String input) {
        assert input != null : "Input should not be null";
        this.input = input;
    }

    /**
     * Executes the command to add a Todo task to the task list.
     * Saves updated tasks to storage and displays confirmation via UI.
     *
     * @param tasks   the task list to update
     * @param ui      the user interface for displaying messages
     * @param storage the storage for saving tasks
     * @throws ElenaException if input format is invalid
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ElenaException {
        assert input.startsWith("todo") : "Input must start with 'todo'";

        String description = input.substring(5).trim();
        assert !description.isEmpty() : "Todo description should not be empty";

        if (description.isEmpty()) {
            throw ElenaException.emptyTodo();
        }

        Todo todo = new Todo(description);
        tasks.add(todo);
        ui.showMessage("Got it. I've added this task:\n  "
                + todo + "\nNow you have " + tasks.size() + " tasks in the list.");
        storage.save(tasks.getAll());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
