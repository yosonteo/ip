import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Jone {
    private static final String FILE_PATH = "./data/duke.txt";
    private static Storage storage = new Storage(FILE_PATH);

    public static void main(String[] args) throws JoneException {
        ArrayList<Task> tasks = new ArrayList<>();
        List<String> saved = storage.load();
        for (String line : saved) {
            tasks.add(Task.fromSaveFormat(line));
        }

        Scanner sc = new Scanner(System.in);

        String indent = "      ";
        String greet = "Hello! I'm Jone!\n"
                + "What can I do for you?";
        String exit = "Bye. Hope to see you again soon!";

        System.out.println(greet);

        while (true) {
            String input = sc.nextLine();

            try {
                if (input.equals("bye")) {
                    System.out.println(indent + exit);
                    break;

                } else if (input.equals("list")) {
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println(indent + (i + 1) + ". " + tasks.get(i));
                    }

                } else if (input.startsWith("mark ")) {     //need add spacing after to distinguish "mark" and "marking"
                    int idx = Integer.parseInt(input.substring(5).trim()) - 1;
                    if (idx < 0 || idx >= tasks.size()) {
                        throw new JoneException("Invalid task number.");
                    }
                    tasks.get(idx).mark();
                    System.out.println(indent + "I've marked this task as done:");
                    System.out.println(indent + "   " + tasks.get(idx));
                    storage.save(toStringList(tasks));

                } else if (input.startsWith("unmark ")) {
                    int idx = Integer.parseInt(input.substring(7).trim()) - 1;
                    if (idx < 0 || idx >= tasks.size()) {
                        throw new JoneException("Invalid task number.");
                    }
                    tasks.get(idx).unmark();
                    System.out.println(indent + "I've marked this task as not done yet:");
                    System.out.println(indent + "   " + tasks.get(idx));
                    storage.save(toStringList(tasks));

                } else if (input.equals("todo") || input.startsWith("todo ")) {
                    if (input.trim().equals("todo")) {
                        throw new JoneException("Description of a todo cannot be empty.");
                    }
                    String desc = input.substring(5).trim();
                    Task t = new Todo(desc);
                    tasks.add(t);
                    System.out.println(indent + "I've added this task:");
                    System.out.println(indent + "   " + t);
                    System.out.println(indent + "Now you have " + tasks.size() + " tasks in the list.");
                    storage.save(toStringList(tasks));

                } else if (input.equals("deadline") || input.startsWith("deadline ")) {
                    Task t = getDeadline(input);
                    tasks.add(t);
                    System.out.println(indent + "I've added this task:");
                    System.out.println(indent + "   " + t);
                    System.out.println(indent + "Now you have " + tasks.size() + " tasks in the list.");
                    storage.save(toStringList(tasks));

                } else if (input.equals("event") || input.startsWith("event ")) {
                    Task t = getEvent(input);
                    tasks.add(t);
                    System.out.println(indent + "I've added this task:");
                    System.out.println(indent + "   " + t);
                    System.out.println(indent + "Now you have " + tasks.size() + " tasks in the list.");
                    storage.save(toStringList(tasks));

                } else if (input.startsWith("delete ")) {
                    int idx = Integer.parseInt(input.substring(7).trim()) - 1;
                    if (idx < 0 || idx >= tasks.size()) {
                        throw new JoneException("Invalid task number.");
                    }
                    Task removed = tasks.remove(idx);  //ArrayList auto-shifts elements
                    System.out.println(indent + "I've removed this task:");
                    System.out.println(indent + "   " + removed);
                    System.out.println(indent + "Now you have " + tasks.size() + " tasks in the list.");
                    storage.save(toStringList(tasks));

                } else {
                    throw new JoneException("Sorry, I don't know what that means.");

                }
            } catch (JoneException e) {
                System.out.println(indent + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println(indent + "Invalid task number.");
            }
        }
    }

    private static Task getEvent(String input) throws JoneException {
        if (input.trim().equals("event")) {
            throw new JoneException("Event must be in the format: event <desc> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>");
        }
        String body = input.substring(6).trim();
        int fromPos = body.indexOf("/from");
        int toPos = body.indexOf("/to");
        if (fromPos == -1 || toPos == -1) {
            throw new JoneException("Event must be in the format: event <desc> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>");
        }
        String desc = body.substring(0, fromPos).trim();
        String start = body.substring(fromPos + 5, toPos).trim();
        String end = body.substring(toPos + 3).trim();
        return new Event(desc, start, end);
    }

    private static Task getDeadline(String input) throws JoneException {
        if (input.trim().equals("deadline")) {
            throw new JoneException("Deadline must be in the format: deadline <desc> /by <yyyy-MM-dd>");
        }
        String body = input.substring(9).trim();
        int pos = body.indexOf("/by");
        if (pos == -1) {
            throw new JoneException("Deadline must be in the format: deadline <desc> /by <yyyy-MM-dd>");
        }
        String desc = body.substring(0, pos).trim();
        String by = body.substring(pos + 3).trim();
        return new Deadline(desc, by);
    }

    private static ArrayList<String> toStringList(ArrayList<Task> tasks) {
        ArrayList<String> lines = new ArrayList<>();
        for (Task t : tasks) {
            lines.add(t.toSaveFormat());
        }
        return lines;
    }
}