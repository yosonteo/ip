package jone;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;

public class StorageTest {

    @Test
    public void load_noFile_returnsEmptyList() {
        Storage s = new Storage("nonexistent.txt");
        List<String> tasks = s.load();
        assertTrue(tasks.isEmpty(), "Loading from nonexistent file should give an empty list");
    }

    @Test
    public void save_thenLoad_returnsSameData() {
        String testFile = "testdata.txt";
        Storage s = new Storage(testFile);

        List<String> original = List.of("todo read book", "deadline project /by 2025-09-06");
        s.save(original);

        List<String> loaded = s.load();
        assertEquals(original.size(), loaded.size(), "Should load the same number of lines");
        assertEquals(original.get(0), loaded.get(0), "First line should match");
        assertEquals(original.get(1), loaded.get(1), "Second line should match");

        // cleanup
        new File(testFile).delete();
    }
}
