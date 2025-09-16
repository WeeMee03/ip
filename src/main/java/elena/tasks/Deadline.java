package elena.tasks;

/**
 * Represents a task with a specific deadline.
 */
public class Deadline extends Task {
    /** The due date/time of the task in formatted string. */
    private final String by;

    /**
     * Constructs a new Deadline task.
     *
     * @param description task description
     * @param by due date/time in formatted string
     */
    public Deadline(String description, String by) {
        super(description, TaskType.DEADLINE);
        this.by = by;
    }

    /**
     * Returns the string format used for saving this task to storage.
     *
     * @return formatted string for storage
     */
    @Override
    public String toSaveFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by;
    }

    /**
     * Returns a string representation of the task for display to the user.
     *
     * @return string describing the task
     */
    @Override
    public String toString() {
        return "[D]" + super.toString().substring(3) + " (by: " + by + ")";
    }
}
