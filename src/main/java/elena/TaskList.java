package elena;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of tasks and provides methods to manipulate it.
 */
public class TaskList {
    private List<Task> tasks;

    /** Creates an empty TaskList. */
    public TaskList() { tasks = new ArrayList<>(); }

    /** Creates a TaskList with pre-existing tasks. */
    public TaskList(List<Task> tasks) { this.tasks = new ArrayList<>(tasks); }

    /** Adds a task to the list. */
    public void add(Task task) { tasks.add(task); }

    /** Deletes a task by index and returns it. */
    public Task delete(int index) { return tasks.remove(index); }

    /** Gets a task by index. */
    public Task get(int index) { return tasks.get(index); }

    /** Returns the number of tasks. */
    public int size() { return tasks.size(); }

    /** Returns all tasks as a new list. */
    public List<Task> getAll() { return new ArrayList<>(tasks); }

    /** Returns true if there are no tasks. */
    public boolean isEmpty() { return tasks.isEmpty(); }
}
