import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public List<String> load() {
        List<String> lines = new ArrayList<>();
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } else {
                lines = Files.readAllLines(Paths.get(filePath));
            }
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
        return lines;
    }

    public void save(List<String> tasks) {
        try {
            FileWriter fw = new FileWriter(filePath);
            for (String task : tasks) {
                fw.write(task + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}

