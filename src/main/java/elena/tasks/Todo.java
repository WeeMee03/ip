package elena.tasks;

/**
 * Represents a Todo task.
 */
public class Todo extends Task {

    /**
     * Constructs a Todo with a description.
     *
     * @param description task description
     */
    public Todo(String description) {
        super(description, TaskType.TODO);
    }

    @Override
    public String toSaveFormat() {
        return type.getCode() + " | " + (isDone ? "1" : "0") + " | " + description;
    }
}
