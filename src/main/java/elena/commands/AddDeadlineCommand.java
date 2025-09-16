package elena.commands;

import elena.core.ElenaException;
import elena.core.Storage;
import elena.core.TaskList;
import elena.core.Ui;
import elena.tasks.Deadline;

import java.time.format.DateTimeParseException;

/**
 * Represents a command to add a Deadline task.
 */
public class AddDeadlineCommand implements Command {
    private final String input;

    /**
     * Constructs a new AddDeadlineCommand.
     *
     * @param input the full user input starting with "deadline"
     */
    public AddDeadlineCommand(String input) {
        assert input != null : "Input should not be null";
        this.input = input;
    }

    /**
     * Executes the command to add a Deadline task to the task list.
     * Saves updated tasks to storage and displays confirmation via UI.
     *
     * @param tasks   the task list to update
     * @param ui      the user interface for displaying messages
     * @param storage the storage for saving tasks
     * @throws ElenaException if input format is invalid
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ElenaException {
        assert input.startsWith("deadline") : "Input must start with 'deadline'";

        String content = input.substring(9).trim();
        assert !content.isEmpty() : "Deadline content should not be empty";

        String[] parts = content.split("/by", 2);
        assert parts.length == 2 : "Deadline input must contain /by";
        assert !parts[0].trim().isEmpty() : "Deadline description should not be empty";
        assert !parts[1].trim().isEmpty() : "Deadline date/time should not be empty";

        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw ElenaException.emptyDeadline();
        }

        try {
            Deadline deadline = new Deadline(parts[0].trim(), parts[1].trim());
            tasks.add(deadline);
            ui.showMessage("Got it. I've added this task:\n  "
                    + deadline + "\nNow you have " + tasks.size() + " tasks in the list.");
            storage.save(tasks.getAll());
        } catch (DateTimeParseException e) {
            throw new ElenaException(
                    "Invalid date/time format! Use yyyy-MM-dd HHmm, e.g., 2025-09-02 1800"
            );
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
