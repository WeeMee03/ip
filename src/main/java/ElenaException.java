public class ElenaException extends Exception {
    public ElenaException(String message) {
        super(message);
    }

    public static ElenaException emptyTodo() {
        return new ElenaException("Your todo cannot be empty! Please type something after 'todo'.");
    }

    public static ElenaException emptyDeadline() {
        return new ElenaException("Deadline missing description or /by time! Use: deadline <desc> /by <yyyy-MM-dd HHmm>");
    }

    public static ElenaException emptyEvent() {
        return new ElenaException("Event missing description or time! Use: event <desc> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>");
    }

    public static ElenaException invalidCommand(String command) {
        return new ElenaException("Oops! I don't understand '" + command + "'. Try a valid command.");
    }

    public static ElenaException invalidTaskNumber() {
        return new ElenaException("The task number you provided is invalid. Make sure it exists in your list.");
    }

    public static ElenaException nonIntegerTaskNumber() {
        return new ElenaException("Task number must be a whole number. Example: mark 2");
    }

    public static ElenaException invalidDateTime(String dateTime) {
        return new ElenaException("Invalid date/time format: '" + dateTime + "'. Use yyyy-MM-dd HHmm, e.g., 2019-12-02 1800.");
    }
}
