package elena.core;

import elena.tasks.Task;
import elena.tasks.Todo;
import elena.tasks.Deadline;
import elena.tasks.Event;
import elena.tasks.Recurring;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles saving and loading tasks to and from a text file.
 */
public class Storage {

    private static final DateTimeFormatter INPUT_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    private final Path filePath;

    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    public List<Task> load() {
        List<Task> tasks = new ArrayList<>();
        try {
            if (!Files.exists(filePath)) {
                createFileIfMissing();
                return tasks;
            }

            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                try {
                    Task task = decode(line);
                    tasks.add(task);
                } catch (Exception e) {
                    System.out.println("Skipping corrupted line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }

        return tasks;
    }

    public void save(List<Task> tasks) {
        try {
            Files.createDirectories(filePath.getParent());
            try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
                for (Task task : tasks) {
                    writer.write(task.toSaveFormat());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    private Task decode(String line) {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task;
        switch (type) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                task = decodeDeadline(parts);
                break;
            case "E":
                task = decodeEvent(parts);
                break;
            case "R":
                task = decodeRecurring(parts);
                break;
            default:
                throw new IllegalArgumentException("Invalid task type: " + type);
        }

        if (isDone) {
            task.markAsDone();
        }

        return task;
    }

    private Task decodeRecurring(String[] parts) {
        return new Recurring(parts[2], parts[3]);
    }

    private Task decodeDeadline(String[] parts) {
        return new Deadline(parts[2], parts[3]);
    }

    private Task decodeEvent(String[] parts) {
        return new Event(parts[2], parts[3], parts[4]);
    }

    private void createFileIfMissing() throws IOException {
        Files.createDirectories(filePath.getParent());
        Files.createFile(filePath);
    }
}
