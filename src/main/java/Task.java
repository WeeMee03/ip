public abstract class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType type;

    public Task(String description, TaskType type) {
        this.description = description;
        this.type = type;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    public TaskType getType() {
        return type;
    }

    public void markAsDone() { isDone = true; }
    public void markAsNotDone() { isDone = false; }

    @Override
    public String toString() {
        return "[" + type.getCode() + "][" + getStatusIcon() + "] " + description;
    }
}
