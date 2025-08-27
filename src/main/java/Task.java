public class Task {
    protected final String description;
    protected boolean done;

    public Task(String description) {
        this.description = description;
        this.done = false;
    }

    public void mark() {
        this.done = true;
    }

    public void unmark() {
        this.done = false;
    }

    public boolean isDone() {
        return done;
    }

    public String getDescription() {
        return description;
    }

    protected String status() {
        return done ? "X" : " ";
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", status(), description);
    }
}
