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

            try {
                // Use Parser to parse and validate command and arguments
                String[] parsed = Parser.parse(line);
                String command = parsed[0];
                String arguments = parsed[1];

                switch (command) {
                    case COMMAND_TODO:
                        tasks.add(new Todo(arguments));
                        ui.showTaskAdded(tasks.get(tasks.size() - 1), tasks.size());
                        break;

                    case COMMAND_DEADLINE:
                        String[] deadlineParts = arguments.split(" /by ", 2);
                        if (deadlineParts.length < 2) {
                            throw new JoneException("Deadline must be in the format: deadline <desc> /by <yyyy-MM-dd>");
                        }
                        tasks.add(new Deadline(deadlineParts[0], deadlineParts[1]));
                        ui.showTaskAdded(tasks.get(tasks.size() - 1), tasks.size());
                        break;

                    case COMMAND_EVENT:
                        String[] eventParts = arguments.split(" /from | /to ");
                        if (eventParts.length < 3) {
                            throw new JoneException("Event must be in the format: event <desc> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>");
                        }
                        tasks.add(new Event(eventParts[0], eventParts[1], eventParts[2]));
                        ui.showTaskAdded(tasks.get(tasks.size() - 1), tasks.size());
                        break;

                    case COMMAND_LIST:
                        tasks.printTasks();
                        break;

                    case COMMAND_MARK:
                        int markIndex = Integer.parseInt(arguments) - 1;
                        tasks.get(markIndex).mark();
                        ui.showTaskMarked(tasks.get(markIndex), true);
                        break;

                    case COMMAND_UNMARK:
                        int unmarkIndex = Integer.parseInt(arguments) - 1;
                        tasks.get(unmarkIndex).unmark();
                        ui.showTaskMarked(tasks.get(unmarkIndex), false);
                        break;

                    case COMMAND_DELETE:
                        int deleteIndex = Integer.parseInt(arguments) - 1;
                        Task removed = tasks.remove(deleteIndex);
                        ui.showTaskRemoved(removed, tasks.size());
                        break;

                    case "find":
                        tasks.findTasks(arguments);
                        break;

                    case COMMAND_BYE:
                        ui.showExit();
                        isExit = true;
                        break;

                    default:
                        ui.showError("I'm sorry, but I don't know what that means.");
                        break;
                }
                storage.save(tasks.getAllTasks());

            } catch (JoneException e) {
                ui.showError(e.getMessage());
            } catch (Exception e) {
                ui.showError("An unexpected error occurred: " + e.getMessage());
            }
        }
        sc.close();
    }

    public static void main(String[] args) {
        new Jone("data/jone.txt").run();
    }
}
