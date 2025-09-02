package elena;

public class ExitCommand implements Command {
    public void execute(TaskList tasks, Ui ui, Storage storage) { ui.showMessage("Bye. Hope to see you again!"); }
    public boolean isExit() { return true; }
}