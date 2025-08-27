import java.util.Scanner;

public class Jone {
    public static void main(String[] args) {
        int taskCount = 0;
        Task[] tasks = new Task[100];
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
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println(indent + (i + 1) + ". " + tasks[i]);
                    }

                } else if (input.startsWith("mark ")) {     //need add spacing after to distinguish "mark" and "marking"
                    int idx = Integer.parseInt(input.substring(5).trim()) - 1;
                    if (idx < 0 || idx >= taskCount) {
                        throw new JoneException("Invalid task number.");
                    }
                    tasks[idx].mark();
                    System.out.println(indent + "I've marked this task as done:");
                    System.out.println(indent + "   " + tasks[idx]);

                } else if (input.startsWith("unmark ")) {
                    int idx = Integer.parseInt(input.substring(7).trim()) - 1;
                    if (idx < 0 || idx >= taskCount) {
                        throw new JoneException("Invalid task number.");
                    }
                    tasks[idx].unmark();
                    System.out.println(indent + "I've marked this task as not done yet:");
                    System.out.println(indent + "   " + tasks[idx]);

                } else if (input.equals("todo") || input.startsWith("todo ")) {
                    if (input.trim().equals("todo")) {
                        throw new JoneException("Description of a todo cannot be empty.");
                    }
                    String desc = input.substring(5).trim();
                    Task t = new Todo(desc);
                    tasks[taskCount] = t;
                    taskCount++;
                    System.out.println(indent + "I've added this task:");
                    System.out.println(indent + "   " + t);
                    System.out.println(indent + "Now you have " + taskCount + " tasks in the list.");

                } else if (input.equals("deadline") || input.startsWith("deadline ")) {
                    Task t = getDeadline(input);
                    tasks[taskCount] = t;
                    taskCount++;
                    System.out.println(indent + "I've added this task:");
                    System.out.println(indent + "   " + t);
                    System.out.println(indent + "Now you have " + taskCount + " tasks in the list.");

                } else if (input.equals("event") || input.startsWith("event ")) {
                    Task t = getEvent(input);
                    tasks[taskCount] = t;
                    taskCount++;
                    System.out.println(indent + "I've added this task:");
                    System.out.println(indent + "   " + t);
                    System.out.println(indent + "Now you have " + taskCount + " tasks in the list.");

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
            throw new JoneException("Event must be in the format: event <desc> /from <start> /to <end>");
        }
        String body = input.substring(6).trim();
        int fromPos = body.indexOf("/from");
        int toPos = body.indexOf("/to");
        if (fromPos == -1 || toPos == -1) {
            throw new JoneException("Event must be in the format: event <desc> /from <start> /to <end>");
        }
        String desc = body.substring(0, fromPos).trim();
        String start = body.substring(fromPos + 5, toPos).trim();
        String end = body.substring(toPos + 3).trim();
        return new Event(desc, start, end);
    }

    private static Task getDeadline(String input) throws JoneException {
        if (input.trim().equals("deadline")) {
            throw new JoneException("Deadline must be in the format: deadline <desc> /by <time>");
        }
        String body = input.substring(9).trim();
        int pos = body.indexOf("/by");
        if (pos == -1) {
            throw new JoneException("Deadline must be in the format: deadline <desc> /by <time>");
        }
        String desc = body.substring(0, pos).trim();
        String by = body.substring(pos + 3).trim();
        return new Deadline(desc, by);
    }
}