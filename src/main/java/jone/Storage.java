package jone;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private static final String FILE_ERROR = "Unable to access save file.";

    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws JoneException {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                assert line != null && !line.trim().isEmpty() : "Empty or null line in save file";
                tasks.add(Task.fromSaveFormat(line));
            }
        } catch (IOException e) {
            throw new JoneException(FILE_ERROR);
        }
        return tasks;
    }

    public void save(ArrayList<Task> tasks) throws JoneException {
        assert tasks != null : "Task list cannot be null when saving";
        try {
            FileWriter writer = new FileWriter(filePath);
            for (Task task : tasks) {
                writer.write(task.toSaveFormat() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            throw new JoneException(FILE_ERROR);
        }
    }
}
