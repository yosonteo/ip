package jone;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task t) {
        tasks.add(t);
    }

    public Task remove(int index) {
        return tasks.remove(index);
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    public void printTasks() {
        Ui ui = new Ui();
        ui.showMessage("Here are your tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            ui.showMessage("  " + (i + 1) + ". " + tasks.get(i));
        }
    }

    public String findTasks(String keyword) {
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getDescription().contains(keyword)) {
                sb.append("  ").append(i + 1).append(". ").append(tasks.get(i)).append("\n");
            }
        }
        String result = sb.toString().trim();
        return result.isEmpty() ? "No matching tasks found." : result;
    }

    public String getTasksAsString() {
        if (tasks.isEmpty()) {
            return "You don't have any tasks yet.";
        }
        StringBuilder sb = new StringBuilder("Here are your tasks:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append("  ").append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString().trim();
    }
}

