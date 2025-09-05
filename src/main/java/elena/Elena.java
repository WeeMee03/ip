package elena;

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
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Storage storage = new Storage("./data/elena.txt");
        List<Task> tasks = storage.load();

        printWelcomeMessage();

        while (true) {
            String input = scanner.nextLine().trim();

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

                Task task = Parser.parseTask(input);
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
        System.out.println(" Hello! I'm elena.Elena 🤖");
        System.out.println(" What can I do for you?");
        printLine();
    }

    private static boolean handleExit(String input) {
        if (input.equalsIgnoreCase("bye")) {
            printLine();
            System.out.println(" Bye. Hope to see you again soon!");
            printLine();
            return true;
        }
        return false;
    }

    private static boolean handleList(String input, List<Task> tasks) {
        if (!input.equalsIgnoreCase("list")) {
            return false;
        }

        printLine();
        if (tasks.isEmpty()) {
            System.out.println(" No tasks yet.");
        } else {
            System.out.println(" Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + ". " + tasks.get(i));
            }
        }
        printLine();
        return true;
    }

    /**
     * Handles mark and unmark commands for tasks.
     * @param input user input
     * @param tasks current list of tasks
     * @return true if handled, false otherwise
     * @throws ElenaException if task number is invalid
     */
    private static boolean handleMarkUnmark(String input, List<Task> tasks)
            throws ElenaException {
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
            if (index < 0 || index >= tasks.size()) {
                throw ElenaException.invalidTaskNumber();
            }

            Task task = tasks.get(index);
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
     * @param input user input
     * @param tasks current list of tasks
     * @return true if handled, false otherwise
     * @throws ElenaException if task number is invalid
     */
    private static boolean handleDelete(String input, List<Task> tasks)
            throws ElenaException {
        if (!input.toLowerCase().startsWith("delete ")) {
            return false;
        }

        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new ElenaException("Usage: delete <task number>");
        }

        try {
            int index = Integer.parseInt(parts[1]) - 1;
            if (index < 0 || index >= tasks.size()) {
                throw ElenaException.invalidTaskNumber();
            }

            Task removed = tasks.remove(index);
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
     * Prints information about a newly added task.
     * @param task the task added
     * @param size current number of tasks
     */
    private static void printTaskAdded(Task task, int size) {
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
        printLine();
        System.out.println(" OOPS!!! " + message);
        printLine();
    }
}
