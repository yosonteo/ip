package jone;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    public void markAsDone_marksTaskCorrectly() {
        Task t = new Todo("read book");
        t.mark();
        assertTrue(t.isDone(), "Task should be marked as done");
    }

    @Test
    public void toString_formatCorrect() {
        Task t = new Todo("read book");
        assertEquals("[T][ ] read book", t.toString());
        t.mark();
        assertEquals("[T][X] read book", t.toString());
    }
}
