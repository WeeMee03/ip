public abstract class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType type;

    public Task(String description, TaskType type) {
        this.description = description;
        this.type = type;
        this.isDone = false;
    }

    public void markAsDone() { isDone = true; }
    public void markAsNotDone() { isDone = false; }
    public boolean isDone() { return isDone; }

    public abstract String toSaveFormat();

    @Override
    public String toString() {
        return "[" + type.getCode() + "][" + (isDone ? "X" : " ") + "] " + description;
    }
}