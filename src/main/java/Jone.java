import java.util.Scanner;

public class Jone {
    public static void main(String[] args) {
        int taskCount = 0;
        Task[] tasks = new Task[100];
        Scanner sc = new Scanner(System.in);

        String indent = "      ";
        String greet = "Hello! I'm Jone! \n"
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
                System.out.println("I've marked this task as done:");
                System.out.println(indent + tasks[idx]);
            } else if (input.startsWith("unmark ")) {
                int idx = Integer.parseInt(input.substring(7).trim()) - 1;
                tasks[idx].unmark();
                System.out.println("I've marked this task as not done yet:");
                System.out.println(indent + tasks[idx]);
            } else {
                tasks[taskCount] = new Task(input);
                taskCount++;
                System.out.println(indent + "added: " + input);
            }
        }
    }
}