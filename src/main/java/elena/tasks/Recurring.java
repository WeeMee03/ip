package elena.tasks;

public class Recurring extends Task {
    private final String recurrence; // e.g., "weekly", "daily"

    public Recurring(String description, String recurrence) {
        super(description, TaskType.RECURRING); // Add RECURRING to TaskType
        this.recurrence = recurrence;
    }

    public String getRecurrence() {
        return recurrence;
    }

    @Override
    public String toSaveFormat() {
        return type.getCode() + " | " + (isDone ? "1" : "0") + " | " + description + " | " + recurrence;
    }

    @Override
    public String toString() {
        return super.toString() + " (recurs: " + recurrence + ")";
    }
}
