package elena;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles saving and loading tasks to/from a text file.
 */
public class Storage {
    private final Path filePath;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    /**
     * Constructs a Storage object.
     * @param filePath path to the file where tasks are stored
     */
    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    /**
     * Loads tasks from the storage file.
     * Creates file if it does not exist.
     * Skips corrupted lines.
     * @return list of tasks
     */
    public List<Task> load() {
        List<Task> tasks = new ArrayList<>();
        try {
            if (!Files.exists(filePath)) {
                Files.createDirectories(filePath.getParent());
                Files.createFile(filePath);
                return tasks; // empty list on first run
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

    /**
     * Saves all tasks to the storage file.
     * @param tasks list of tasks to save
     */
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

    /**
     * Decodes a single line from the storage file into a Task object.
     * @param line the line from file
     * @return the decoded Task
     * @throws IllegalArgumentException if task type is invalid
     */
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
                LocalDateTime by = LocalDateTime.parse(parts[3], INPUT_FORMAT);
                task = new Deadline(description, parts[3]);
                break;
            case "E":
                LocalDateTime from = LocalDateTime.parse(parts[3], INPUT_FORMAT);
                LocalDateTime to = LocalDateTime.parse(parts[4], INPUT_FORMAT);
                task = new Event(description, parts[3], parts[4]);
                break;
            default:
                throw new IllegalArgumentException("Invalid task type: " + type);
        }

        if (isDone) task.markAsDone();
        return task;
    }
}
