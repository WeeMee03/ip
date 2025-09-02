public class ListCommand implements Command {
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (tasks.isEmpty()) ui.showMessage("No tasks yet.");
        else {
            ui.showMessage("Here are your tasks:");
            for (int i = 0; i < tasks.size(); i++) ui.showMessage((i + 1) + ". " + tasks.get(i));
        }
    }
    public boolean isExit() { return false; }
}