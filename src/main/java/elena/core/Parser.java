package elena.core;

import elena.tasks.Task;
import elena.tasks.Todo;
import elena.tasks.Deadline;
import elena.tasks.Event;
import elena.tasks.Recurring;

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

    /**
     * Parses a string input into a Task object.
     *
     * @param input user input
     * @return Task object
     * @throws ElenaException if input is invalid
     */
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
            case "recurring":
                return parseRecurring(rest);
            default:
                throw ElenaException.invalidCommand(input);
        }
    }

    private static Recurring parseRecurring(String rest) throws ElenaException {
        String[] parts = rest.split("/recur", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new ElenaException("Usage: recurring <description> /recur <daily|weekly|monthly>");
        }
        String description = parts[0].trim();
        String recurrence = parts[1].trim().toLowerCase();
        return new Recurring(description, recurrence);
    }

    private static Todo parseTodo(String rest) throws ElenaException {
        if (rest.isEmpty()) throw ElenaException.emptyTodo();
        return new Todo(rest);
    }

    private static Deadline parseDeadline(String rest) throws ElenaException {
        String[] parts = rest.split("/by", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw ElenaException.emptyDeadline();
        }
        LocalDateTime by = parseDateTime(parts[1].trim());
        return new Deadline(parts[0].trim(), formatDateTime(by));
    }

    private static Event parseEvent(String rest) throws ElenaException {
        String[] parts = rest.split("/from", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty()) throw ElenaException.emptyEvent();

        String[] times = parts[1].split("/to", 2);
        if (times.length < 2 || times[0].trim().isEmpty() || times[1].trim().isEmpty()) {
            throw ElenaException.emptyEvent();
        }

        LocalDateTime from = parseDateTime(times[0].trim());
        LocalDateTime to = parseDateTime(times[1].trim());
        return new Event(parts[0].trim(), formatDateTime(from), formatDateTime(to));
    }

    private static LocalDateTime parseDateTime(String s) throws ElenaException {
        try {
            return LocalDateTime.parse(s, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new ElenaException(
                    "Invalid date/time format! Use yyyy-MM-dd HHmm. Example: 2025-09-02 1800");
        }
    }

    private static String formatDateTime(LocalDateTime dt) {
        return dt.format(OUTPUT_FORMAT);
    }
}
