public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    public boolean isDone() {
        return isDone;
    }

    public void markAsDone() { isDone = true; }
    public void markAsNotDone() { isDone = false; }

    // Format for saving to file
    public abstract String toSaveFormat();

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
