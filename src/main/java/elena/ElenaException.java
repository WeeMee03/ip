package elena;

/**
 * Represents an exception thrown by Elena for invalid input or operations.
 */
public class ElenaException extends Exception {
    /**
     * Constructs an ElenaException with a message.
     *
     * @param message error message
     */
    public ElenaException(String message) {
        super(message);
    }

    /**
     * Returns an exception for empty todo input.
     *
     * @return ElenaException for empty todo
     */
    public static ElenaException emptyTodo() {
        return new ElenaException("Your todo cannot be empty! Please type something after 'todo'.");
    }

    /**
     * Returns an exception for invalid deadline input.
     *
     * @return ElenaException for invalid deadline
     */
    public static ElenaException emptyDeadline() {
        return new ElenaException(
                "Deadline missing description or /by time! Use: deadline <desc> /by <time>"
        );
    }

    /**
     * Returns an exception for invalid event input.
     *
     * @return ElenaException for invalid event
     */
    public static ElenaException emptyEvent() {
        return new ElenaException(
                "Event missing description or time! Use: event <desc> /from <start> /to <end>"
        );
    }

    /**
     * Returns an exception for invalid command.
     *
     * @param command invalid command string
     * @return ElenaException for invalid command
     */
    public static ElenaException invalidCommand(String command) {
        return new ElenaException("Oops! I don't understand '" + command + "'. Try a valid command.");
    }

    /**
     * Returns an exception for invalid task number.
     *
     * @return ElenaException for invalid task number
     */
    public static ElenaException invalidTaskNumber() {
        return new ElenaException(
                "The task number you provided is invalid. Make sure it exists in your list."
        );
    }

    /**
     * Returns an exception when task number is not an integer.
     *
     * @return ElenaException for non-integer task number
     */
    public static ElenaException nonIntegerTaskNumber() {
        return new ElenaException("Task number must be a whole number. Example: mark 2");
    }
}
