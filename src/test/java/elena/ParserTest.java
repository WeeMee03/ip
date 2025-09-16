package elena;

import elena.core.Elena;
import elena.core.ElenaException;
import elena.tasks.Deadline;
import elena.tasks.Task;
import elena.tasks.Todo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ParserTest {

    @Test
    void parseTodo_validInput_returnsTodoTask()
            throws ElenaException {
        Task task = Elena.Parser.parseTask("todo finish homework");
        Assertions.assertTrue(task instanceof Todo);
        Assertions.assertEquals("[T][ ] finish homework", task.toString());
    }

    @Test
    void parseTodo_emptyDescription_throwsException() {
        Assertions.assertThrows(
                ElenaException.class,
                () -> Elena.Parser.parseTask("todo"),
                "Todo without description should throw exception"
        );
    }

    @Test
    void parseDeadline_validInput_returnsDeadlineTask()
            throws ElenaException {
        Task task = Elena.Parser.parseTask("deadline submit report /by 2025-09-05 1800");
        Assertions.assertTrue(task instanceof Deadline);
        Assertions.assertTrue(task.toString().contains("submit report"));
        Assertions.assertTrue(task.toString().contains("Sep 05 2025 18:00"));
    }

    @Test
    void parseEvent_invalidFormat_throwsException() {
        Assertions.assertThrows(
                ElenaException.class,
                () -> Elena.Parser.parseTask("event project meeting /from 2025-09-05 1800"),
                "Event missing /to part should throw exception"
        );
    }

    @Test
    void parseInvalidCommand_throwsException() {
        Assertions.assertThrows(
                ElenaException.class,
                () -> Elena.Parser.parseTask("nonsense blah"),
                "Invalid command should throw exception"
        );
    }
}
