import java.util.Scanner;

public class Jone {
    public static void main(String[] args) {
        int taskCount = 0;
        String[] tasks = new String[100];
        Scanner sc = new Scanner(System.in);
        String greet = "Hello! I'm Jone! \n"
                     + "What can I do for you?";
        String exit = "Bye. Hope to see you again soon!";

        System.out.println(greet);

        while (true) {
            String input = sc.nextLine();

            if (input.equals("bye")) {
                System.out.println("      " + exit);
                break;
            } else if (input.equals("list")) {
                for (int i = 0; i < taskCount; i++) {
                    System.out.println("      " + (i + 1) + ". " + tasks[i]);
                }
            } else {
                tasks[taskCount] = input;
                taskCount++;
                System.out.println("      added: " + input);
                }
            }
    }
}