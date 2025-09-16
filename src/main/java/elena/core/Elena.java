package elena.core;

import elena.tasks.Task;
import elena.tasks.Deadline;
import elena.tasks.Event;
import elena.tasks.Todo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * The main class of the Elena task management application.
 * Handles user input, task operations, and program flow.
 */
public class Elena {

    /**
     * Entry point of the program.
     * Initializes storage, loads tasks, and enters the main command loop.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Storage storage = new Storage("./data/elena.txt");
        List<Task> tasks = storage.load();
        assert tasks != null : "Task list must not be null after loading";

        printWelcomeMessage();

        while (true) {
            String input = scanner.nextLine().trim();
            assert input != null : "User input should not be null";

            try {
                if (handleExit(input)) {
                    break;
                }

                if (handleList(input, tasks)) {
                    continue;
                }

                if (handleMarkUnmark(input, tasks)) {
                    storage.save(tasks);
                    continue;
                }

                if (handleDelete(input, tasks)) {
                    storage.save(tasks);
                    continue;
                }

                if (handleFind(input, tasks)) {
                    continue;
                }

                // Parse all other tasks through Parser
                Task task = Parser.parseTask(input);
                assert task != null : "Parsed task must not be null";
                tasks.add(task);
                printTaskAdded(task, tasks.size());
                storage.save(tasks);

            } catch (ElenaException e) {
                printError(e.getMessage());
            } catch (Exception e) {
                printError("Something went wrong: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void printWelcomeMessage() {
        printLine();
        System.out.println(" Hello! I'm elena.core.Elena 🤖");
        System.out.println(" What can I do for you?");
        printLine();
    }

    private static boolean handleExit(String input) {
        assert input != null : "Input must not be null";
        if (input.equalsIgnoreCase("bye")) {
            printLine();
            System.out.println(" Bye. Hope to see you again soon!");
            printLine();
            return true;
        }
        return false;
    }

    private static boolean handleList(String input, List<Task> tasks) {
        assert input != null : "Input must not be null";
        assert tasks != null : "Task list must not be null";
        if (!input.equalsIgnoreCase("list")) {
            return false;
        }

        printLine();
        if (tasks.isEmpty()) {
            System.out.println(" No tasks yet.");
        } else {
            System.out.println(" Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                assert tasks.get(i) != null : "Task must not be null in list";
                System.out.println(" " + (i + 1) + ". " + tasks.get(i));
            }
        }
        printLine();
        return true;
    }

    /**
     * Handles mark and unmark commands for tasks.
     *
     * @param input user input
     * @param tasks current list of tasks
     * @return true if handled, false otherwise
     * @throws ElenaException if task number is invalid
     */
    private static boolean handleMarkUnmark(String input, List<Task> tasks)
            throws ElenaException {
        assert input != null : "Input must not be null";
        assert tasks != null : "Task list must not be null";

        boolean isMark = input.toLowerCase().startsWith("mark ");
        boolean isUnmark = input.toLowerCase().startsWith("unmark ");
        if (!isMark && !isUnmark) {
            return false;
        }

        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new ElenaException(
                    "Usage: " + (isMark ? "mark" : "unmark") + " <task number>");
        }

        try {
            int index = Integer.parseInt(parts[1]) - 1;
            assert index >= 0 : "Task index cannot be negative";
            if (index < 0 || index >= tasks.size()) {
                throw ElenaException.invalidTaskNumber();
            }

            Task task = tasks.get(index);
            assert task != null : "Task at index must not be null";
            printLine();
            if (isMark) {
                task.markAsDone();
                System.out.println(" Nice! I've marked this task as done:");
            } else {
                task.markAsNotDone();
                System.out.println(" OK, I've marked this task as not done yet:");
            }
            System.out.println("   " + task);
            printLine();
            return true;
        } catch (NumberFormatException e) {
            throw ElenaException.nonIntegerTaskNumber();
        }
    }

    /**
     * Handles delete command for tasks.
     *
     * @param input user input
     * @param tasks current list of tasks
     * @return true if handled, false otherwise
     * @throws ElenaException if task number is invalid
     */
    private static boolean handleDelete(String input, List<Task> tasks)
            throws ElenaException {
        assert input != null : "Input must not be null";
        assert tasks != null : "Task list must not be null";

        if (!input.toLowerCase().startsWith("delete ")) {
            return false;
        }

        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new ElenaException("Usage: delete <task number>");
        }

        try {
            int index = Integer.parseInt(parts[1]) - 1;
            assert index >= 0 : "Task index cannot be negative";
            if (index < 0 || index >= tasks.size()) {
                throw ElenaException.invalidTaskNumber();
            }

            Task removed = tasks.remove(index);
            assert removed != null : "Removed task must not be null";
            printLine();
            System.out.println(" Noted. I've removed this task:");
            System.out.println("   " + removed);
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
            printLine();
            return true;
        } catch (NumberFormatException e) {
            throw ElenaException.nonIntegerTaskNumber();
        }
    }

    /**
     * Handles find command for tasks.
     *
     * @param input user input
     * @param tasks current list of tasks
     * @return true if handled, false otherwise
     * @throws ElenaException if keyword is missing
     */
    private static boolean handleFind(String input, List<Task> tasks)
            throws ElenaException {
        assert input != null : "Input must not be null";
        assert tasks != null : "Task list must not be null";

        if (!input.toLowerCase().startsWith("find ")) {
            return false;
        }

        String keyword = input.substring(5).trim();
        if (keyword.isEmpty()) {
            throw new ElenaException("Usage: find <keyword>");
        }

        printLine();
        System.out.println(" Here are the matching tasks in your list:");
        int count = 0;
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            assert task != null : "Task must not be null when searching";
            if (task.toString().toLowerCase().contains(keyword.toLowerCase())) {
                count++;
                System.out.println(" " + count + ". " + task);
            }
        }
        if (count == 0) {
            System.out.println(" No matching tasks found.");
        }
        printLine();
        return true;
    }

    /**
     * Prints information about a newly added task.
     *
     * @param task the task added
     * @param size current number of tasks
     */
    private static void printTaskAdded(Task task, int size) {
        assert task != null : "Task added must not be null";
        assert size >= 0 : "Task list size must be non-negative";
        printLine();
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + size + " tasks in the list.");
        printLine();
    }

    /** Prints a horizontal line separator. */
    private static void printLine() {
        System.out.println("____________________________________________________________");
    }

    private static void printError(String message) {
        assert message != null : "Error message must not be null";
        printLine();
        System.out.println(" OOPS!!! " + message);
        printLine();
    }

    /**
     * Generates a response for the user's chat message.
     *
     * @param input user input
     * @return Elena's response string
     */
    public String getResponse(String input) {
        assert input != null : "Input to getResponse must not be null";
        return "Elena heard: " + input;
    }

    /**
     * Parses user input into Task objects.
     * Only handles actual tasks: Todo, Deadline, Event.
     */
    public static class Parser {

        private static final DateTimeFormatter INPUT_FORMAT =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        private static final DateTimeFormatter OUTPUT_FORMAT =
                DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

        public static Task parseTask(String input) throws ElenaException {
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
            if (rest.isEmpty()) {
                throw ElenaException.emptyTodo();
            }
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
            if (parts.length < 2 || parts[0].trim().isEmpty()) {
                throw ElenaException.emptyEvent();
            }

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
}
