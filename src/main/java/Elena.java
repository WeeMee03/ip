import java.util.Scanner;

public class Elena {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] tasks = new String[100]; // fixed-size array for tasks
        int taskCount = 0; // number of tasks stored
        String input;

        // Welcome message
        printLine();
        System.out.println(" Hello! I'm Elena 🤖");
        System.out.println(" What can I do for you?");
        printLine();

        while (true) {
            input = scanner.nextLine().trim();

            // Exit condition
            if (input.equalsIgnoreCase("bye")) {
                printLine();
                System.out.println(" Bye. Hope to see you again soon!");
                printLine();
                break;
            }

            // List all tasks
            if (input.equalsIgnoreCase("list")) {
                printLine();
                if (taskCount == 0) {
                    System.out.println(" No tasks yet.");
                } else {
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println(" " + (i + 1) + ". " + tasks[i]);
                    }
                }
                printLine();
                continue;
            }

            // Add task
            if (taskCount < tasks.length) {
                tasks[taskCount] = input;
                taskCount++;
                printLine();
                System.out.println(" added: " + input);
                printLine();
            } else {
                printLine();
                System.out.println(" Task list is full! Cannot add more.");
                printLine();
            }
        }

        scanner.close();
    }

    // Helper method to print a separator line
    private static void printLine() {
        System.out.println("____________________________________________________________");
    }
}
