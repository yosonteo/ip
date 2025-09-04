public class Deadline extends Task {
    private final String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    public Deadline(String description, String by, boolean done) {
        super(description, done);
        this.by = by;
    }

    @Override
    public String toString() {
        return String.format("[D][%s] %s (by: %s)", status(), description, by);
    }

    @Override
    public String toSaveFormat() {
        return "D | " + (done ? 1 : 0) + " | " + description + " | " + by;
    }
}
