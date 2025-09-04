package jone;

public class Ui {
    private final String indent = "      ";

    public void showWelcome() {
        System.out.println("Hello! I'm Jone!\nWhat can I do for you?");
    }

    public void showExit() {
        System.out.println(indent + "Bye. Hope to see you again soon!");
    }

    public void showMessage(String msg) {
        System.out.println(indent + msg);
    }

    public void showTaskAdded(Task t, int size) {
        showMessage("I've added this task:");
        showMessage("   " + t);
        showMessage("Now you have " + size + " tasks in the list.");
    }

    public void showTaskRemoved(Task t, int size) {
        showMessage("I've removed this task:");
        showMessage("   " + t);
        showMessage("Now you have " + size + " tasks in the list.");
    }

    public void showTaskMarked(Task t, boolean done) {
        if (done) {
            showMessage("I've marked this task as done:");
        } else {
            showMessage("I've marked this task as not done yet:");
        }
        showMessage("   " + t);
    }

    public void showError(String msg) {
        System.out.println(indent + msg);
    }
}
