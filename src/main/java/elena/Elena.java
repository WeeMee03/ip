package elena;

import java.util.List;
import java.util.Scanner;

public class Elena {

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

    private static void handleMarkUnmark(String input, List<Task> tasks) throws ElenaException {
        boolean isMark = input.toLowerCase().startsWith("mark ");
        String[] parts = input.split(" ");
        if (parts.length < 2) throw new ElenaException("Usage: " + (isMark ? "mark" : "unmark") + " <task number>");
        try {
            int index = Integer.parseInt(parts[1]) - 1;
            if (index < 0 || index >= tasks.size()) throw ElenaException.invalidTaskNumber();
            if (isMark) {
                tasks.get(index).markAsDone();
                printLine();
                System.out.println(" Nice! I've marked this task as done:");
            } else {
                tasks.get(index).markAsNotDone();
                printLine();
                System.out.println(" OK, I've marked this task as not done yet:");
            }
            System.out.println("   " + tasks.get(index));
            printLine();
        } catch (NumberFormatException e) {
            throw ElenaException.nonIntegerTaskNumber();
        }
    }

    private static void handleDelete(String input, List<Task> tasks) throws ElenaException {
        String[] parts = input.split(" ");
        if (parts.length < 2) throw new ElenaException("Usage: delete <task number>");
        try {
            int index = Integer.parseInt(parts[1]) - 1;
            if (index < 0 || index >= tasks.size()) throw ElenaException.invalidTaskNumber();
            Task removed = tasks.remove(index);
            printLine();
            System.out.println(" Noted. I've removed this task:");
            System.out.println("   " + removed);
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
            printLine();
        } catch (NumberFormatException e) {
            throw ElenaException.nonIntegerTaskNumber();
        }
    }

    private static void printTaskAdded(Task task, int size) {
        printLine();
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + size + " tasks in the list.");
        printLine();
    }

    private static void printLine() {
        System.out.println("____________________________________________________________");
    }
}
