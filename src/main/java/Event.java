import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    public Event(String description, String fromString, String toString) throws ElenaException {
        super(description, TaskType.EVENT);
        try {
            this.from = LocalDateTime.parse(fromString, INPUT_FORMAT);
            this.to = LocalDateTime.parse(toString, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw ElenaException.invalidDateTime(fromString + " or " + toString);
        }
    }

    @Override
    public String toSaveFormat() {
        return type.getCode() + " | " + (isDone ? "1" : "0") + " | " + description + " | " + from.format(INPUT_FORMAT) + " | " + to.format(INPUT_FORMAT);
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + from.format(OUTPUT_FORMAT) + " to: " + to.format(OUTPUT_FORMAT) + ")";
    }

    public LocalDateTime getFrom() { return from; }
    public LocalDateTime getTo() { return to; }
}
