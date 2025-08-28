import java.util.Scanner;

public class Elena {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;

        printLine();
        System.out.println(" Hello! I'm Elena 🤖");
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

                if (input.toLowerCase().startsWith("mark ") || input.toLowerCase().startsWith("unmark ")) {
                    handleMarkUnmark(input, tasks, taskCount);
                    continue;
                }

                // Todo
                if (input.equalsIgnoreCase("todo") || input.toLowerCase().startsWith("todo ")) {
                    String description = "";
                    if (input.length() > 4) {
                        description = input.substring(4).trim();
                    }
                    if (description.isEmpty()) throw ElenaException.emptyTodo();
                    tasks[taskCount++] = new Todo(description);
                    printTaskAdded(tasks[taskCount - 1], taskCount);
                    continue;
                }

                // Deadline
                if (input.equalsIgnoreCase("deadline") || input.toLowerCase().startsWith("deadline ")) {
                    String content = input.length() > 8 ? input.substring(8).trim() : "";
                    String[] parts = content.split("/by", 2);
                    if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty())
                        throw ElenaException.emptyDeadline();
                    tasks[taskCount++] = new Deadline(parts[0].trim(), parts[1].trim());
                    printTaskAdded(tasks[taskCount - 1], taskCount);
                    continue;
                }

                // Event
                if (input.equalsIgnoreCase("event") || input.toLowerCase().startsWith("event ")) {
                    String content = input.length() > 5 ? input.substring(5).trim() : "";
                    String[] parts = content.split("/from", 2);
                    if (parts.length < 2 || parts[0].trim().isEmpty()) throw ElenaException.emptyEvent();
                    String[] times = parts[1].split("/to", 2);
                    if (times.length < 2 || times[0].trim().isEmpty() || times[1].trim().isEmpty())
                        throw ElenaException.emptyEvent();
                    tasks[taskCount++] = new Event(parts[0].trim(), times[0].trim(), times[1].trim());
                    printTaskAdded(tasks[taskCount - 1], taskCount);
                    continue;
                }

                throw ElenaException.invalidCommand(input);

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

    private static void handleMarkUnmark(String input, Task[] tasks, int taskCount) throws ElenaException {
        boolean isMark = input.toLowerCase().startsWith("mark ");
        String[] parts = input.split(" ");
        if (parts.length < 2) throw new ElenaException("Usage: " + (isMark ? "mark" : "unmark") + " <task number>");
        try {
            int index = Integer.parseInt(parts[1]) - 1;
            if (index < 0 || index >= taskCount) throw ElenaException.invalidTaskNumber();
            if (isMark) {
                tasks[index].markAsDone();
                printLine();
                System.out.println(" Nice! I've marked this task as done:");
            } else {
                tasks[index].markAsNotDone();
                printLine();
                System.out.println(" OK, I've marked this task as not done yet:");
            }
            System.out.println("   " + tasks[index]);
            printLine();
        } catch (NumberFormatException e) {
            throw ElenaException.nonIntegerTaskNumber();
        }
    }

    private static void printTaskAdded(Task task, int taskCount) {
        printLine();
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        printLine();
    }

    private static void printLine() {
        System.out.println("____________________________________________________________");
    }
}
