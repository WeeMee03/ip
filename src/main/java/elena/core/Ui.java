package elena.core;

import java.util.Scanner;

/**
 * Handles all input/output operations with the user.
 */
public class Ui {
    private Scanner scanner;

    /** Constructs a new Ui with an input scanner. */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /** Displays the welcome message. */
    public void showWelcome() {
        showLine();
        System.out.println(" Hello! I'm elena.core.Elena 🤖");
        System.out.println(" What can I do for you?");
        showLine();
    }

    /** Displays a horizontal line separator. */
    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    /** Displays a message to the user. */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /** Displays an error message to the user. */
    public void showError(String message) {
        System.out.println(" OOPS!!! " + message);
    }

    /** Reads a line of input from the user. */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /** Closes the input scanner. */
    public void close() {
        scanner.close();
    }
}
