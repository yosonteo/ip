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

            if (input.equals("bye")) {
                System.out.println(indent + exit);
                break;

            } else if (input.equals("list")) {
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(indent + (i + 1) + ". " + tasks[i]);
                }

            } else if (input.startsWith("mark ")) {     //need add spacing after to distinguish "mark" and "marking"
                int idx = Integer.parseInt(input.substring(5).trim()) - 1;
                tasks[idx].mark();
                System.out.println(indent + "I've marked this task as done:");
                System.out.println(indent + "   " + tasks[idx]);

            } else if (input.startsWith("unmark ")) {
                int idx = Integer.parseInt(input.substring(7).trim()) - 1;
                tasks[idx].unmark();
                System.out.println(indent + "I've marked this task as not done yet:");
                System.out.println(indent + "   " + tasks[idx]);

            } else if (input.startsWith("todo ")) {
                String desc = input.substring(5).trim();
                Task t = new Todo(desc);
                tasks[taskCount] = t;
                taskCount++;
                System.out.println(indent + "I've added this task:");
                System.out.println(indent + "   " + t);
                System.out.println(indent + "Now you have " + taskCount + " tasks in the list.");

            } else if (input.startsWith("deadline ")) {     //format: deadline <desc> /by <when>
                String body = input.substring(9).trim();
                int pos = body.indexOf("/by");
                String desc = body.substring(0, pos).trim();
                String by = body.substring(pos + 3).trim();
                Task t = new Deadline(desc, by);
                tasks[taskCount] = t;
                taskCount++;
                System.out.println(indent + "I've added this task:");
                System.out.println(indent + "   " + t);
                System.out.println(indent + "Now you have " + taskCount + " tasks in the list.");

            } else if (input.startsWith("event ")) {    //format: event <desc> /from <start> /to <end>
                String body = input.substring(6).trim();
                int fromPos = body.indexOf("/from");
                int toPos = body.indexOf("/to");
                String desc = body.substring(0, fromPos).trim();
                String start = body.substring(fromPos + 5, toPos).trim();
                String end = body.substring(toPos + 3).trim();
                Task t = new Event(desc, start, end);
                tasks[taskCount] = t;
                taskCount++;
                System.out.println(indent + "I've added this task:");
                System.out.println(indent + "   " + t);
                System.out.println(indent + "Now you have " + taskCount + " tasks in the list.");

            } else {
                tasks[taskCount] = new Task(input);
                taskCount++;
                System.out.println(indent + "added: " + input);
            }
        }
    }
}