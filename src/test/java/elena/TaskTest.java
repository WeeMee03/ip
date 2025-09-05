package elena;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void markAsDoneAndNotDone_shouldToggleCorrectly() {
        Task t = new Todo("read book"); // using Todo subclass
        assertFalse(t.isDone(), "New task should not be done initially");

        t.markAsDone();
        assertTrue(t.isDone(), "Task should be marked as done");

        t.markAsNotDone();
        assertFalse(t.isDone(), "Task should be marked as not done again");
    }

    @Test
    void toString_shouldShowTypeAndStatus() {
        Task t = new Todo("buy milk");

        String expectedNotDone = "[T][ ] buy milk";  // T from TaskType.TODO
        assertEquals(expectedNotDone, t.toString());

        t.markAsDone();
        String expectedDone = "[T][X] buy milk";
        assertEquals(expectedDone, t.toString());
    }
}
