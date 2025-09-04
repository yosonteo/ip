public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    public Todo(String description, boolean done) {
        super(description, done);
    }

    @Override
    public String toString() {
        return String.format("[T][%s] %s", status(), description);
    }

    @Override
    public String toSaveFormat() {
        return "T | " + (done ? 1 : 0) + " | " + description;
    }
}
