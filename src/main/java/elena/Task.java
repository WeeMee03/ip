package elena;

/**
 * Abstract base class for all tasks.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType type;

    /**
     * Constructs a Task with a description and type.
     *
     * @param description task description
     * @param type task type
     */
    public Task(String description, TaskType type) {
        this.description = description;
        this.type = type;
        this.isDone = false;
    }

    /** Marks the task as done. */
    public void markAsDone() {
        isDone = true;
    }

    /** Marks the task as not done. */
    public void markAsNotDone() {
        isDone = false;
    }

    /** Returns true if the task is done. */
    public boolean isDone() {
        return isDone;
    }

    /** Returns the string used for saving to file. */
    public abstract String toSaveFormat();

    @Override
    public String toString() {
        return "[" + type.getCode() + "][" + (isDone ? "X" : " ") + "] " + description;
    }
}
