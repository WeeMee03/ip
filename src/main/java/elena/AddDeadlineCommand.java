package elena;

import java.time.format.DateTimeParseException;

public class AddDeadlineCommand implements Command {
    private final String input;

    public AddDeadlineCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ElenaException {
        String content = input.substring(9).trim();
        String[] parts = content.split("/by", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty())
            throw ElenaException.emptyDeadline();

        try {
            Deadline deadline = new Deadline(parts[0].trim(), parts[1].trim());
            tasks.add(deadline);
            ui.showMessage("Got it. I've added this task:\n  " + deadline + "\nNow you have " + tasks.size() + " tasks in the list.");
            storage.save(tasks.getAll());
        } catch (DateTimeParseException e) {
            throw new ElenaException("Invalid date/time format! Use yyyy-MM-dd HHmm, e.g., 2025-09-02 1800");
        }
    }

    @Override
    public boolean isExit() { return false; }
}
