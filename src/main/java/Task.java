public abstract class Task {
    protected final String description;
    protected boolean done;

    public Task(String description) {
        this.description = description;
        this.done = false;
    }

    public Task(String description, boolean done) {
        this.description = description;
        this.done = done;
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

    // Must be implemented by subclasses
    public abstract String toSaveFormat();

    public static Task fromSaveFormat(String line) throws JoneException {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean done = parts[1].equals("1");
        String desc = parts[2];

        switch (type) {
            case "T":
                return new Todo(desc, done);
            case "D":
                return new Deadline(desc, parts[3], done);
            case "E":
                return new Event(desc, parts[3], parts[4], done);
            default:
                return new Todo(desc, done); // fallback
        }
    }
}
