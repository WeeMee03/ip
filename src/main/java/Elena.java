import java.util.Scanner;

public class Elena {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;
        String input;

        // Welcome message
        printLine();
        System.out.println(" Hello! I'm Elena 🤖");
        System.out.println(" What can I do for you?");
        printLine();

        while (true) {
            input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("bye")) {
                printLine();
                System.out.println(" Bye. Hope to see you again soon!");
                printLine();
                break;
            }

            if (input.equalsIgnoreCase("list")) {
                printLine();
                if (taskCount == 0) {
                    System.out.println(" No tasks yet.");
                } else {
                    System.out.println(" Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println(" " + (i + 1) + "." + tasks[i]);
                    }
                }
                printLine();
                continue;
            }

            // Mark task
            if (input.toLowerCase().startsWith("mark ")) {
                try {
                    int index = Integer.parseInt(input.split(" ")[1]) - 1;
                    if (index >= 0 && index < taskCount) {
                        tasks[index].markAsDone();
                        printLine();
                        System.out.println(" Nice! I've marked this task as done:");
                        System.out.println("   " + tasks[index]);
                        printLine();
                    } else {
                        System.out.println(" Invalid task number.");
                    }
                } catch (Exception e) {
                    System.out.println(" Usage: mark <task number>");
                }
                continue;
            }

            // Unmark task
            if (input.toLowerCase().startsWith("unmark ")) {
                try {
                    int index = Integer.parseInt(input.split(" ")[1]) - 1;
                    if (index >= 0 && index < taskCount) {
                        tasks[index].markAsNotDone();
                        printLine();
                        System.out.println(" OK, I've marked this task as not done yet:");
                        System.out.println("   " + tasks[index]);
                        printLine();
                    } else {
                        System.out.println(" Invalid task number.");
                    }
                } catch (Exception e) {
                    System.out.println(" Usage: unmark <task number>");
                }
                continue;
            }

            // Add tasks based on type
            if (input.toLowerCase().startsWith("todo ")) {
                String description = input.substring(5).trim();
                if (!description.isEmpty() && taskCount < tasks.length) {
                    tasks[taskCount] = new Todo(description);
                    taskCount++;
                    printLine();
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + tasks[taskCount - 1]);
                    System.out.println(" Now you have " + taskCount + " tasks in the list.");
                    printLine();
                }
                continue;
            }

            if (input.toLowerCase().startsWith("deadline ")) {
                try {
                    String[] parts = input.substring(9).split("/by", 2);
                    String description = parts[0].trim();
                    String by = parts[1].trim();
                    if (!description.isEmpty() && taskCount < tasks.length) {
                        tasks[taskCount] = new Deadline(description, by);
                        taskCount++;
                        printLine();
                        System.out.println(" Got it. I've added this task:");
                        System.out.println("   " + tasks[taskCount - 1]);
                        System.out.println(" Now you have " + taskCount + " tasks in the list.");
                        printLine();
                    }
                } catch (Exception e) {
                    System.out.println(" Usage: deadline <description> /by <time>");
                }
                continue;
            }

            if (input.toLowerCase().startsWith("event ")) {
                try {
                    String[] parts = input.substring(6).split("/from", 2);
                    String description = parts[0].trim();
                    String[] times = parts[1].split("/to", 2);
                    String from = times[0].trim();
                    String to = times[1].trim();
                    if (!description.isEmpty() && taskCount < tasks.length) {
                        tasks[taskCount] = new Event(description, from, to);
                        taskCount++;
                        printLine();
                        System.out.println(" Got it. I've added this task:");
                        System.out.println("   " + tasks[taskCount - 1]);
                        System.out.println(" Now you have " + taskCount + " tasks in the list.");
                        printLine();
                    }
                } catch (Exception e) {
                    System.out.println(" Usage: event <description> /from <time> /to <time>");
                }
                continue;
            }

            // Default: treat as Todo if unknown
            if (taskCount < tasks.length) {
                tasks[taskCount] = new Todo(input);
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

    private static void printLine() {
        System.out.println("____________________________________________________________");
    }
}