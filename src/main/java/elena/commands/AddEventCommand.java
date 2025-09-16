package elena.commands;

import elena.core.ElenaException;
import elena.core.Storage;
import elena.core.TaskList;
import elena.core.Ui;
import elena.tasks.Event;

import java.time.format.DateTimeParseException;

/**
 * Represents a command to add an Event task.
 */
public class AddEventCommand implements Command {
    private final String input;

    /**
     * Constructs a new AddEventCommand.
     *
     * @param input the full user input starting with "event"
     */
    public AddEventCommand(String input) {
        this.input = input;
    }

    /**
     * Executes the command to add an Event task to the task list.
     * Saves updated tasks to storage and displays confirmation via UI.
     *
     * @param tasks   the task list to update
     * @param ui      the user interface for displaying messages
     * @param storage the storage for saving tasks
     * @throws ElenaException if input format is invalid
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ElenaException {
        String content = input.substring(6).trim();
        String[] parts = content.split("/from", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty()) {
            throw ElenaException.emptyEvent();
        }

        String[] times = parts[1].split("/to", 2);
        if (times.length < 2 || times[0].trim().isEmpty() || times[1].trim().isEmpty()) {
            throw ElenaException.emptyEvent();
        }

        try {
            Event event = new Event(parts[0].trim(), times[0].trim(), times[1].trim());
            tasks.add(event);
            ui.showMessage("Got it. I've added this task:\n  "
                    + event + "\nNow you have " + tasks.size() + " tasks in the list.");
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
