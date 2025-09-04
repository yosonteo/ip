package jone;

/**
 * Handles all user interface interactions for the chatbot.
 * The {@code Ui} class is responsible for displaying messages,
 * task updates, errors, and greetings to the user.
 */
public class Ui {
    private final String indent = "      ";

    /**
     * Prints the welcome message when the chatbot starts.
     */
    public void showWelcome() {
        System.out.println("Hello! I'm Jone!\nWhat can I do for you?");
    }

    /**
     * Prints the exit message when the chatbot is terminated.
     */
    public void showExit() {
        System.out.println(indent + "Bye. Hope to see you again soon!");
    }

    /**
     * Prints a general message to the user with indentation.
     *
     * @param msg The message to be displayed.
     */
    public void showMessage(String msg) {
        System.out.println(indent + msg);
    }

    /**
     * Prints a message when a task is added to the task list.
     *
     * @param t The task that was added.
     * @param size The new number of tasks in the list.
     */
    public void showTaskAdded(Task t, int size) {
        showMessage("I've added this task:");
        showMessage("   " + t);
        showMessage("Now you have " + size + " tasks in the list.");
    }

    /**
     * Prints a message when a task is removed from the task list.
     *
     * @param t The task that was removed.
     * @param size The new number of tasks remaining in the list.
     */
    public void showTaskRemoved(Task t, int size) {
        showMessage("I've removed this task:");
        showMessage("   " + t);
        showMessage("Now you have " + size + " tasks in the list.");
    }

    /**
     * Prints a message when a task is marked as done or undone.
     *
     * @param t The task that was updated.
     * @param done {@code true} if the task is now done,
     *             {@code false} if marked as not done.
     */
    public void showTaskMarked(Task t, boolean done) {
        if (done) {
            showMessage("I've marked this task as done:");
        } else {
            showMessage("I've marked this task as not done yet:");
        }
        showMessage("   " + t);
    }

    /**
     * Prints an error message to the user.
     *
     * @param msg The error message to be displayed.
     */
    public void showError(String msg) {
        System.out.println(indent + msg);
    }
}
