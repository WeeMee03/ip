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
        List<Task> tasks = storage.load(); // load tasks at startup

        printLine();
        System.out.println(" Hello! I'm elena.Elena 🤖");
        System.out.println(" What can I do for you?");
        printLine();

        while (true) {
            String input = scanner.nextLine().trim();

            try {
                if (input.equalsIgnoreCase("bye")) {
                    printLine();
                    System.out.println(" Bye. Hope to see you again soon!");
                    printLine();
                    break;
                }

                if (input.equalsIgnoreCase("list")) {
                    printLine();
                    if (tasks.isEmpty()) {
                        System.out.println(" No tasks yet.");
                    } else {
                        System.out.println(" Here are the tasks in your list:");
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println(" " + (i + 1) + "." + tasks.get(i));
                        }
                    }
                    printLine();
                    continue;
                }

                // Mark / Unmark
                if (input.toLowerCase().startsWith("mark ") || input.toLowerCase().startsWith("unmark ")) {
                    handleMarkUnmark(input, tasks);
                    storage.save(tasks); // auto save
                    continue;
                }

                // Delete
                if (input.toLowerCase().startsWith("delete ")) {
                    handleDelete(input, tasks);
                    storage.save(tasks); // auto save
                    continue;
                }

                // Parse all other commands through elena.Parser
                Task task = Parser.parseTask(input);
                tasks.add(task);
                printTaskAdded(task, tasks.size());
                storage.save(tasks); // auto save

            } catch (ElenaException e) {
                printLine();
                System.out.println(" OOPS!!! " + e.getMessage());
                printLine();
            } catch (Exception e) {
                printLine();
                System.out.println(" OOPS!!! Something went wrong: " + e.getMessage());
                printLine();
            }
        }

        scanner.close();
    }

    /**
     * Handles mark and unmark commands for tasks.
     * @param input user input
     * @param tasks current list of tasks
     * @throws ElenaException if task number is invalid
     */
    private static void handleMarkUnmark(String input, List<Task> tasks) throws ElenaException {
        // method body unchanged
    }

    /**
     * Handles delete command for tasks.
     * @param input user input
     * @param tasks current list of tasks
     * @throws ElenaException if task number is invalid
     */
    private static void handleDelete(String input, List<Task> tasks) throws ElenaException {
        // method body unchanged
    }

    /**
     * Prints information about a newly added task.
     * @param task the task added
     * @param size current number of tasks
     */
    private static void printTaskAdded(Task task, int size) {
        // method body unchanged
    }

    /** Prints a horizontal line separator. */
    private static void printLine() {
        System.out.println("____________________________________________________________");
    }
}
