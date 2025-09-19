package elena.core;

import elena.tasks.Task;

import java.util.List;
import java.util.Scanner;

/**
 * The main class of the Elena task management application.
 * Handles user input, task operations, and program flow.
 */
public class Elena {

    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_EXIT = "bye";

    private final Storage storage;
    private final List<Task> tasks;
    private final Scanner scanner;

    /**
     * Constructs the Elena application with a storage file path.
     *
     * @param filePath path to the storage file
     */
    public Elena(String filePath) {
        this.storage = new Storage(filePath);
        this.tasks = storage.load();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Entry point of the program.
     * Initializes storage, loads tasks, and enters the main command loop.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        Elena elena = new Elena("./data/elena.txt");
        elena.run();
    }

    /**
     * Main application loop.
     * Continuously reads user input and handles commands until exit.
     */
    public void run() {
        printWelcomeMessage();

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;

            try {
                if (handleCommand(input)) break;
            } catch (ElenaException e) {
                printError(e.getMessage());
            } catch (Exception e) {
                printError("Unexpected error: " + e.getMessage());
            }
        }
        scanner.close();
    }

    /**
     * Handles a single user command.
     *
     * @param input user input string
     * @return true if the program should exit, false otherwise
     * @throws ElenaException if command is invalid or task index is invalid
     */
    private boolean handleCommand(String input) throws ElenaException {
        String command = input.split(" ")[0].toLowerCase();

        switch (command) {
            case COMMAND_EXIT:
                printExitMessage();
                return true;
            case COMMAND_LIST:
                printTaskList();
                return false;
            case "mark":
            case "unmark":
                handleMarkUnmark(input);
                storage.save(tasks);
                return false;
            case "delete":
                handleDelete(input);
                storage.save(tasks);
                return false;
            case "find":
                handleFind(input);
                return false;
            default:
                // Parse all other tasks through Parser
                Task task = Parser.parseTask(input);
                tasks.add(task);
                printTaskAdded(task, tasks.size());
                storage.save(tasks);
                return false;
        }
    }

    /**
     * Prints a welcome message to the user.
     */
    private void printWelcomeMessage() {
        printLine();
        System.out.println(" Hello! I'm Elena 🤖");
        System.out.println(" What can I do for you?");
        printLine();
    }

    /**
     * Prints a goodbye message to the user.
     */
    private void printExitMessage() {
        printLine();
        System.out.println(" Bye. Hope to see you again soon!");
        printLine();
    }

    /**
     * Prints the current task list.
     */
    private void printTaskList() {
        printLine();
        if (tasks.isEmpty()) {
            System.out.println(" No tasks yet.");
        } else {
            System.out.println(" Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                assert task != null : "Task must not be null in list";
                System.out.println(" " + (i + 1) + ". " + task);
            }
        }
        printLine();
    }

    /**
     * Handles mark and unmark commands for tasks.
     *
     * @param input user input
     * @throws ElenaException if task number is invalid
     */
    private void handleMarkUnmark(String input) throws ElenaException {
        boolean isMark = input.toLowerCase().startsWith("mark ");
        int index = CommandParser.parseTaskIndex(input);
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
    }

    /**
     * Handles delete command for tasks.
     *
     * @param input user input
     * @throws ElenaException if task number is invalid
     */
    private void handleDelete(String input) throws ElenaException {
        int index = CommandParser.parseTaskIndex(input);
        Task removed = tasks.remove(index);
        assert removed != null : "Removed task must not be null";

        printLine();
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + removed);
        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
        printLine();
    }

    /**
     * Handles find command for tasks.
     *
     * @param input user input
     * @throws ElenaException if keyword is missing
     */
    private void handleFind(String input) throws ElenaException {
        String keyword = input.substring(5).trim();
        if (keyword.isEmpty()) throw new ElenaException("Usage: find <keyword>");

        printLine();
        System.out.println(" Here are the matching tasks in your list:");
        int count = 0;
        for (Task task : tasks) {
            assert task != null : "Task must not be null when searching";
            if (task.toString().toLowerCase().contains(keyword.toLowerCase())) {
                count++;
                System.out.println(" " + count + ". " + task);
            }
        }
        if (count == 0) System.out.println(" No matching tasks found.");
        printLine();
    }

    /**
     * Prints information about a newly added task.
     *
     * @param task the task added
     * @param size current number of tasks
     */
    private void printTaskAdded(Task task, int size) {
        assert task != null : "Task added must not be null";
        assert size >= 0 : "Task list size must be non-negative";

        printLine();
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + size + " tasks in the list.");
        printLine();
    }

    /** Prints a horizontal line separator. */
    private void printLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Prints error messages to the user.
     *
     * @param message the error message
     */
    private void printError(String message) {
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
     * Handles user input from GUI and returns a response string.
     */
    public String handleInput(String input) {
        if (input == null || input.isEmpty()) return "";
        try {
            if (handleCommand(input)) {
                return "Bye. Hope to see you again soon!";
            }
        } catch (ElenaException e) {
            return "OOPS!!! " + e.getMessage();
        } catch (Exception e) {
            return "Unexpected error: " + e.getMessage();
        }

        // For 'list' command, we want the task list as a string
        if (input.toLowerCase().startsWith("list")) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < tasks.size(); i++) {
                sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
            }
            return sb.toString();
        }

        return "Task updated.";
    }
}
