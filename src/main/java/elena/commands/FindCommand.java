package elena.commands;

import elena.core.Storage;
import elena.core.TaskList;
import elena.core.Ui;
import elena.tasks.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Finds tasks that match a given keyword in the description.
 */
public class FindCommand implements Command {
    private final String keyword;

    /**
     * Constructs a FindCommand with the given search keyword.
     *
     * @param keyword Keyword to search for.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        List<Task> matched = new ArrayList<>();
        for (Task task : tasks.getAll()) {
            if (task.description.toLowerCase().contains(keyword.toLowerCase())) {
                matched.add(task);
            }
        }

        ui.showMessage("Here are the matching tasks in your list:");
        if (matched.isEmpty()) {
            ui.showMessage(" No tasks match your search.");
        } else {
            for (int i = 0; i < matched.size(); i++) {
                ui.showMessage((i + 1) + ". " + matched.get(i));
            }
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
