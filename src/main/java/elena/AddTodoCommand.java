package elena;

/**
 * Represents a command to add a Todo task.
 */
public class AddTodoCommand implements Command {
    private final String input;

    public AddTodoCommand(String input) { this.input = input; }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ElenaException {
        String description = input.substring(5).trim();
        if (description.isEmpty()) throw ElenaException.emptyTodo();

        Todo todo = new Todo(description);
        tasks.add(todo);
        ui.showMessage("Got it. I've added this task:\n  " + todo + "\nNow you have " + tasks.size() + " tasks in the list.");
        storage.save(tasks.getAll());
    }

    @Override
    public boolean isExit() { return false; }
}
