public class Event extends Task {
    private final String start;
    private final String end;

    public Event(String description, String start, String end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    public Event(String description, String start, String end, boolean done) {
        super(description, done);
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return String.format("[E][%s] %s (from: %s to: %s)", status(), description, start, end);
    }

    @Override
    public String toSaveFormat() {
        return "E | " + (done ? 1 : 0) + " | " + description + " | " + start + " | " + end;
    }
}
