import java.util.Scanner;

public class Jone {
    public static void main(String[] args) {
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
            }

            System.out.println("      " + input);
        }
    }
}