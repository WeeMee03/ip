package elena;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    public static Task parseTask(String input) throws ElenaException {
        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();
        String rest = parts.length > 1 ? parts[1].trim() : "";

        switch (command) {
            case "todo":
                if (rest.isEmpty()) throw ElenaException.emptyTodo();
                return new Todo(rest);

            case "deadline":
                String[] deadlineParts = rest.split("/by", 2);
                if (deadlineParts.length < 2 || deadlineParts[0].trim().isEmpty() || deadlineParts[1].trim().isEmpty())
                    throw ElenaException.emptyDeadline();
                LocalDateTime by = parseDateTime(deadlineParts[1].trim());
                return new Deadline(deadlineParts[0].trim(), formatDateTime(by));

            case "event":
                String[] eventParts = rest.split("/from", 2);
                if (eventParts.length < 2 || eventParts[0].trim().isEmpty()) throw ElenaException.emptyEvent();
                String[] times = eventParts[1].split("/to", 2);
                if (times.length < 2 || times[0].trim().isEmpty() || times[1].trim().isEmpty())
                    throw ElenaException.emptyEvent();
                LocalDateTime from = parseDateTime(times[0].trim());
                LocalDateTime to = parseDateTime(times[1].trim());
                return new Event(eventParts[0].trim(), formatDateTime(from), formatDateTime(to));

            default:
                throw ElenaException.invalidCommand(input);
        }
    }

    private static LocalDateTime parseDateTime(String s) throws ElenaException {
        try {
            return LocalDateTime.parse(s, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new ElenaException("Invalid date/time format! Use yyyy-MM-dd HHmm. Example: 2025-09-02 1800");
        }
    }

    private static String formatDateTime(LocalDateTime dt) {
        return dt.format(OUTPUT_FORMAT);
    }
}
