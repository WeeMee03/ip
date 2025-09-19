package elena.tasks;

/**
 * Enum for different task types.
 */
public enum TaskType {
    TODO("T"),
    DEADLINE("D"),
    EVENT("E"),
    RECURRING("R");

    private final String code;

    TaskType(String code) {
        this.code = code;
    }

    /** Returns the one-letter code for saving tasks. */
    public String getCode() {
        return code;
    }
}
