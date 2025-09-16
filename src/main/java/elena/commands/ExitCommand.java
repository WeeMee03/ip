package elena.commands;

import elena.core.Storage;
import elena.core.TaskList;
import elena.core.Ui;

/**
 * Command to exit the program.
 */
public class ExitCommand implements Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showMessage("Bye. Hope to see you again!");
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
