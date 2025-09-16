package elena.core;

import elena.tasks.Task;
import elena.tasks.Todo;
import elena.tasks.Deadline;
import elena.tasks.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Parses user input into Task objects.
 * Only handles actual tasks: Todo, Deadline, Event.
 */
public class Parser {

    private static final DateTimeFormatter INPUT_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    public static Task parseTask(String input) throws ElenaException {
        assert input != null : "Input should not be null";

        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();
        String rest = parts.length > 1 ? parts[1].trim() : "";

        switch (command) {
            case "todo":
                return parseTodo(rest);
            case "deadline":
                return parseDeadline(rest);
            case "event":
                return parseEvent(rest);
            default:
                throw ElenaException.invalidCommand(input);
        }
    }

    private static Todo parseTodo(String rest) throws ElenaException {
        assert rest != null : "Todo description should not be null";
        assert !rest.isEmpty() : "Todo description should not be empty";

        if (rest.isEmpty()) {
            throw ElenaException.emptyTodo();
        }
        return new Todo(rest);
    }

    private static Deadline parseDeadline(String rest) throws ElenaException {
        assert rest != null : "Deadline input should not be null";

        String[] parts = rest.split("/by", 2);
        assert parts.length == 2 : "Deadline must have a /by part";
        assert !parts[0].trim().isEmpty() : "Deadline description should not be empty";
        assert !parts[1].trim().isEmpty() : "Deadline date/time should not be empty";

        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw ElenaException.emptyDeadline();
        }

        LocalDateTime by = parseDateTime(parts[1].trim());
        return new Deadline(parts[0].trim(), formatDateTime(by));
    }

    private static Event parseEvent(String rest) throws ElenaException {
        assert rest != null : "Event input should not be null";

        String[] parts = rest.split("/from", 2);
        assert parts.length == 2 : "Event must have /from part";
        assert !parts[0].trim().isEmpty() : "Event description should not be empty";

        String[] times = parts[1].split("/to", 2);
        assert times.length == 2 : "Event must have /to part";
        assert !times[0].trim().isEmpty() : "Event start time should not be empty";
        assert !times[1].trim().isEmpty() : "Event end time should not be empty";

        if (times.length < 2 || times[0].trim().isEmpty() || times[1].trim().isEmpty()) {
            throw ElenaException.emptyEvent();
        }

        LocalDateTime from = parseDateTime(times[0].trim());
        LocalDateTime to = parseDateTime(times[1].trim());
        return new Event(parts[0].trim(), formatDateTime(from), formatDateTime(to));
    }

    private static LocalDateTime parseDateTime(String s) throws ElenaException {
        assert s != null : "Date/time string should not be null";

        try {
            return LocalDateTime.parse(s, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new ElenaException(
                    "Invalid date/time format! Use yyyy-MM-dd HHmm. Example: 2025-09-02 1800");
        }
    }

    private static String formatDateTime(LocalDateTime dt) {
        assert dt != null : "Date/time object should not be null";
        return dt.format(OUTPUT_FORMAT);
    }
}
