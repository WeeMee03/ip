import java.util.Scanner;

public class Elena {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
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

            // Echo the command
            printLine();
            System.out.println(" " + input);
            printLine();
        }

        scanner.close();
    }

    // Helper method to print a separator line
    private static void printLine() {
        System.out.println("____________________________________________________________");
    }
}
