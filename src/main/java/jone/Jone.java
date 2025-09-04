package jone;

import java.util.Scanner;

public class Jone {
    private static final String COMMAND_TODO = "todo";
    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_EVENT = "event";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_MARK = "mark";
    private static final String COMMAND_UNMARK = "unmark";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_BYE = "bye";

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    public Jone(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        TaskList loaded;
        try {
            loaded = new TaskList(storage.load());
        } catch (JoneException e) {
            ui.showError("Failed to load tasks: " + e.getMessage());
            loaded = new TaskList();
        }
        tasks = loaded;
    }

    public void run() {
        ui.showWelcome();
        Scanner sc = new Scanner(System.in);
        boolean isExit = false;

        while (!isExit && sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            String[] parts = line.split(" ", 2);
            String command = parts[0];

            try {
                switch (command) {
                    case COMMAND_TODO:
                        tasks.add(new Todo(parts[1]));
                        ui.showTaskAdded(tasks.get(tasks.size() - 1), tasks.size());
                        break;
                    case COMMAND_DEADLINE:
                        String[] deadlineParts = parts[1].split(" /by ");
                        tasks.add(new Deadline(deadlineParts[0], deadlineParts[1]));
                        ui.showTaskAdded(tasks.get(tasks.size() - 1), tasks.size());
                        break;
                    case COMMAND_EVENT:
                        String[] eventParts = parts[1].split(" /from | /to ");
                        tasks.add(new Event(eventParts[0], eventParts[1], eventParts[2]));
                        ui.showTaskAdded(tasks.get(tasks.size() - 1), tasks.size());
                        break;
                    case COMMAND_LIST:
                        tasks.printTasks();
                        break;
                    case COMMAND_MARK:
                        int markIndex = Integer.parseInt(parts[1]) - 1;
                        tasks.get(markIndex).mark();
                        ui.showTaskMarked(tasks.get(markIndex), true);
                        break;
                    case COMMAND_UNMARK:
                        int unmarkIndex = Integer.parseInt(parts[1]) - 1;
                        tasks.get(unmarkIndex).unmark();
                        ui.showTaskMarked(tasks.get(unmarkIndex), false);
                        break;
                    case COMMAND_DELETE:
                        int deleteIndex = Integer.parseInt(parts[1]) - 1;
                        Task removed = tasks.remove(deleteIndex);
                        ui.showTaskRemoved(removed, tasks.size());
                        break;
                    case "find":
                        if (parts.length < 2) {
                            ui.showError("Please provide a keyword to search for.");
                        } else {
                            tasks.findTasks(parts[1]);
                        }
                        break;
                    case COMMAND_BYE:
                        ui.showExit();
                        isExit = true;
                        break;

                    default:
                        ui.showError("I'm sorry, but I don't know what that means.");
                }
                storage.save(tasks.getAllTasks());
            } catch (Exception e) {
                ui.showError(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Jone("data/jone.txt").run();
    }
}