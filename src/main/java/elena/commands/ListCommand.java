package elena.commands;

import elena.core.Storage;
import elena.core.TaskList;
import elena.core.Ui;

/**
 * Command to list all tasks.
 */
public class ListCommand implements Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        assert tasks != null : "TaskList should not be null";
        assert ui != null : "Ui should not be null";

        if (tasks.isEmpty()) {
            ui.showMessage("No tasks yet.");
        } else {
            ui.showMessage("Here are your tasks:");
            for (int i = 0; i < tasks.size(); i++) {
                assert tasks.get(i) != null : "Task in list should not be null";
                ui.showMessage((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
