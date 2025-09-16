package elena;

/**
 * Interface representing a command in the application.
 */
public interface Command {
    /**
     * Executes the command.
     *
     * @param tasks   the task list
     * @param ui      the user interface
     * @param storage the storage for saving tasks
     * @throws ElenaException if execution fails
     */
    void execute(TaskList tasks, Ui ui, Storage storage) throws ElenaException;

    /**
     * Indicates whether this command exits the application.
     *
     * @return true if this command exits the program, false otherwise
     */
    boolean isExit();
}
