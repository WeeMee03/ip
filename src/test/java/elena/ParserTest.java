package elena;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void parseTodo_validInput_returnsTodoTask() throws ElenaException {
        Task task = Parser.parseTask("todo finish homework");

        assertTrue(task instanceof Todo);
        assertEquals("[T][ ] finish homework", task.toString());
    }

    @Test
    void parseTodo_emptyDescription_throwsException() {
        assertThrows(ElenaException.class,
                () -> Parser.parseTask("todo"),
                "Todo without description should throw exception");
    }

    @Test
    void parseDeadline_validInput_returnsDeadlineTask() throws ElenaException {
        Task task = Parser.parseTask("deadline submit report /by 2025-09-05 1800");

        assertTrue(task instanceof Deadline);
        assertTrue(task.toString().contains("submit report"));
        assertTrue(task.toString().contains("Sep 05 2025 18:00"));
    }

    @Test
    void parseEvent_invalidFormat_throwsException() {
        assertThrows(ElenaException.class,
                () -> Parser.parseTask("event project meeting /from 2025-09-05 1800"),
                "Event missing /to part should throw exception");
    }

    @Test
    void parseInvalidCommand_throwsException() {
        assertThrows(ElenaException.class,
                () -> Parser.parseTask("nonsense blah"),
                "Invalid command should throw exception");
    }
}
