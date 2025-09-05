package elena;

/**
 * Represents an Event task with a start and end time.
 */
public class Event extends Task {
    private final String from;
    private final String to;

    public Event(String description, String from, String to) {
        super(description, TaskType.EVENT);
        this.from = from;
        this.to = to;
    }

    /** Returns the start time. */
    public String getFrom() { return from; }

    /** Returns the end time. */
    public String getTo() { return to; }

    @Override
    public String toSaveFormat() {
        return type.getCode() + " | " + (isDone ? "1" : "0") + " | " + description + " | " + from + " | " + to;
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
