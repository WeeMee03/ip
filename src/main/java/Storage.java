import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private static final String DATA_FOLDER = "data";
    private static final String DATA_FILE = "elena.txt";
    private final Path filePath;

    public Storage() {
        this.filePath = Paths.get(DATA_FOLDER, DATA_FILE);
        createDataFileIfMissing();
    }

    private void createDataFileIfMissing() {
        try {
            File folder = new File(DATA_FOLDER);
            if (!folder.exists()) folder.mkdir();
            File file = filePath.toFile();
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            System.out.println("Error initializing storage: " + e.getMessage());
        }
    }

    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        try (Scanner sc = new Scanner(filePath)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                try {
                    Task task = parseTask(line);
                    tasks.add(task);
                } catch (Exception e) {
                    System.out.println("⚠ Skipping corrupted line: '" + line + "'");
                    Task recovered = recoverTask(line);
                    if (recovered != null) {
                        tasks.add(recovered);
                        System.out.println("   ✔ Recovered as: " + recovered);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("No saved tasks found. Starting fresh!");
        }
        return tasks;
    }

    public void save(ArrayList<Task> tasks) {
        try (FileWriter fw = new FileWriter(filePath.toFile())) {
            for (Task task : tasks) {
                fw.write(task.toSaveFormat() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    private Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) throw new IllegalArgumentException("Insufficient parts");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        switch (type) {
            case "T":
                return new Todo(description, isDone);
            case "D":
                if (parts.length < 4) throw new IllegalArgumentException("Missing deadline date");
                return new Deadline(description, parts[3], isDone);
            case "E":
                if (parts.length < 5) throw new IllegalArgumentException("Missing event times");
                return new Event(description, parts[3], parts[4], isDone);
            default:
                throw new IllegalArgumentException("Unknown task type: " + type);
        }
    }

    // Attempt to recover a corrupted task line
    private Task recoverTask(String line) {
        try {
            if (line.startsWith("T |")) {
                String[] parts = line.split(" \\| ");
                String desc = parts.length > 2 ? parts[2] : "Recovered Todo";
                boolean done = parts.length > 1 && parts[1].equals("1");
                return new Todo(desc, done);
            } else if (line.startsWith("D |")) {
                String[] parts = line.split(" \\| ");
                String desc = parts.length > 2 ? parts[2] : "Recovered Deadline";
                String by = parts.length > 3 ? parts[3] : "Unknown";
                boolean done = parts.length > 1 && parts[1].equals("1");
                return new Deadline(desc, by, done);
            } else if (line.startsWith("E |")) {
                String[] parts = line.split(" \\| ");
                String desc = parts.length > 2 ? parts[2] : "Recovered Event";
                String from = parts.length > 3 ? parts[3] : "Unknown";
                String to = parts.length > 4 ? parts[4] : "Unknown";
                boolean done = parts.length > 1 && parts[1].equals("1");
                return new Event(desc, from, to, done);
            }
        } catch (Exception e) {
            // Recovery failed
        }
        return null; // Cannot recover
    }
}
