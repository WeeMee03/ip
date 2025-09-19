package elena.core;

/**
 * Helper class to parse task indices from commands.
 */
public final class CommandParser {

    private CommandParser() {} // Prevent instantiation

    /**
     * Parses task index from commands like "mark 1" or "delete 2".
     *
     * @param input user input
     * @return zero-based task index
     * @throws ElenaException if invalid or non-integer index
     */
    public static int parseTaskIndex(String input) throws ElenaException {
        String[] parts = input.split(" ");
        if (parts.length < 2) throw new ElenaException("Missing task number.");
        try {
            int index = Integer.parseInt(parts[1]) - 1;
            if (index < 0) throw ElenaException.invalidTaskNumber();
            return index;
        } catch (NumberFormatException e) {
            throw ElenaException.nonIntegerTaskNumber();
        }
    }
}
