package elena;

/**
 * Command to exit the program.
 */
public class ExitCommand implements Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showMessage("Bye. Hope to see you again!");
    }

    @Override
    public boolean isExit() { return true; }
}
