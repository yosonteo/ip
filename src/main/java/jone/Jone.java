package jone;

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

    public String getResponse(String line) {
        try {
            String[] parsed = Parser.parse(line);
            String command = parsed[0];
            String arguments = parsed[1];

            switch (command) {
                case COMMAND_TODO:
                    Task todo = new Todo(arguments);
                    tasks.add(todo);
                    storage.save(tasks.getAllTasks());
                    return ui.showTaskAdded(todo, tasks.size());

                case COMMAND_DEADLINE:
                    String[] deadlineParts = arguments.split(" /by ", 2);
                    if (deadlineParts.length < 2) {
                        return ui.showError("Deadline must be in the format: deadline <desc> /by <yyyy-MM-dd>");
                    }
                    Task deadline = new Deadline(deadlineParts[0], deadlineParts[1]);
                    tasks.add(deadline);
                    storage.save(tasks.getAllTasks());
                    return ui.showTaskAdded(deadline, tasks.size());

                case COMMAND_EVENT:
                    String[] eventParts = arguments.split(" /from | /to ");
                    if (eventParts.length < 3) {
                        return ui.showError("Event must be in the format: event <desc> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>");
                    }
                    Task event = new Event(eventParts[0], eventParts[1], eventParts[2]);
                    tasks.add(event);
                    storage.save(tasks.getAllTasks());
                    return ui.showTaskAdded(event, tasks.size());

                case COMMAND_LIST:
                    return tasks.getTasksAsString();

                case COMMAND_MARK:
                    int markIndex = Integer.parseInt(arguments) - 1;
                    tasks.get(markIndex).mark();
                    storage.save(tasks.getAllTasks());
                    return ui.showTaskMarked(tasks.get(markIndex), true);

                case COMMAND_UNMARK:
                    int unmarkIndex = Integer.parseInt(arguments) - 1;
                    tasks.get(unmarkIndex).unmark();
                    storage.save(tasks.getAllTasks());
                    return ui.showTaskMarked(tasks.get(unmarkIndex), false);

                case COMMAND_DELETE:
                    int deleteIndex = Integer.parseInt(arguments) - 1;
                    Task removed = tasks.remove(deleteIndex);
                    storage.save(tasks.getAllTasks());
                    return ui.showTaskRemoved(removed, tasks.size());

                case "find":
                    return tasks.findTasks(arguments);

                case COMMAND_BYE:
                    return ui.showExit();

                default:
                    return ui.showError("I'm sorry, but I don't know what that means.");
            }
        } catch (JoneException e) {
            return ui.showError(e.getMessage());
        } catch (Exception e) {
            return ui.showError("An unexpected error occurred: " + e.getMessage());
        }
    }
}
