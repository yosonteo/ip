package jone;

public class Todo extends Task {
    private static final String TYPE = "T";

    public Todo(String description) {
        super(description);
    }

    public Todo(String description, boolean done) {
        super(description, done);
    }

    @Override
    public String toString() {
        return String.format("[%s][%s] %s", TYPE, status(), getDescription());
    }

    @Override
    public String toSaveFormat() {
        return TYPE + " | " + (isDone() ? 1 : 0) + " | " + getDescription();
    }
}
