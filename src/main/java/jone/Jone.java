package jone;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jone {
    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

    public Jone(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            List<String> saved = storage.load();
            ArrayList<Task> taskObjects = new ArrayList<>();
            for (String line : saved) {
                taskObjects.add(Task.fromSaveFormat(line));
            }
            tasks = new TaskList(taskObjects);
        } catch (Exception e) {
            ui.showError("Error loading tasks. Starting with empty list.");
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();
        Scanner sc = new Scanner(System.in);

        while (true) {
            String input = sc.nextLine();
            String command = Parser.getCommandWord(input);
            String args = Parser.getArgs(input);

            try {
                switch (command) {
                    case "bye":
                        ui.showExit();
                        return;

                    case "list":
                        tasks.listTasks(ui);
                        break;

                    case "mark":
                        int markIdx = Integer.parseInt(args) - 1;
                        Task marked = tasks.get(markIdx);
                        marked.mark();
                        ui.showTaskMarked(marked, true);
                        break;

                    case "unmark":
                        int unmarkIdx = Integer.parseInt(args) - 1;
                        Task unmarked = tasks.get(unmarkIdx);
                        unmarked.unmark();
                        ui.showTaskMarked(unmarked, false);
                        break;

                    case "todo":
                        if (args.isEmpty()) throw new JoneException("Description of a todo cannot be empty.");
                        Task t = new Todo(args);
                        tasks.add(t);
                        ui.showTaskAdded(t, tasks.size());
                        break;

                    case "deadline":
                        if (args.isEmpty()) throw new JoneException("Description of a deadline cannot be empty.");
                        Task d = getDeadline(args);
                        tasks.add(d);
                        ui.showTaskAdded(d, tasks.size());
                        break;

                    case "event":
                        if (args.isEmpty()) throw new JoneException("Description of an event cannot be empty.");
                        Task e = getEvent(args);
                        tasks.add(e);
                        ui.showTaskAdded(e, tasks.size());
                        break;

                    case "delete":
                        int delIdx = Integer.parseInt(args) - 1;
                        Task removed = tasks.delete(delIdx);
                        ui.showTaskRemoved(removed, tasks.size());
                        break;

                    default:
                        throw new JoneException("Sorry, I don't know what that means.");
                }

                // Save after every command
                storage.save(toStringList(tasks.getAll()));

            } catch (JoneException je) {
                ui.showError(je.getMessage());
            } catch (NumberFormatException nfe) {
                ui.showError("Invalid task number.");
            } catch (Exception e) {
                ui.showError("Unexpected error: " + e.getMessage());
            }
        }
    }

    private static Task getDeadline(String args) throws JoneException {
        int pos = args.indexOf("/by");
        if (pos == -1) {
            throw new JoneException("Deadline must be in the format: deadline <desc> /by <yyyy-MM-dd>");
        }
        String desc = args.substring(0, pos).trim();
        String by = args.substring(pos + 3).trim();
        return new Deadline(desc, by);
    }

    private static Task getEvent(String args) throws JoneException {
        int fromPos = args.indexOf("/from");
        int toPos = args.indexOf("/to");
        if (fromPos == -1 || toPos == -1) {
            throw new JoneException("Event must be in the format: event <desc> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>");
        }
        String desc = args.substring(0, fromPos).trim();
        String start = args.substring(fromPos + 5, toPos).trim();
        String end = args.substring(toPos + 3).trim();
        return new Event(desc, start, end);
    }

    private static ArrayList<String> toStringList(ArrayList<Task> tasks) {
        ArrayList<String> lines = new ArrayList<>();
        for (Task t : tasks) {
            lines.add(t.toSaveFormat());
        }
        return lines;
    }

    public static void main(String[] args) {
        new Jone("./data/duke.txt").run();
    }
}