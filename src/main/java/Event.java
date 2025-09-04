import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected LocalDateTime start;
    protected LocalDateTime end;

    public Event(String description, String start, String end) throws JoneException {
        super(description);
        try {
            this.start = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            this.end = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        } catch (Exception e) {
            throw new JoneException("Invalid datetime format. Please use yyyy-MM-dd HHmm.");
        }
    }

    public Event(String description, String start, String end, boolean done) throws JoneException {
        super(description, done);
        try {
            this.start = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            this.end = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        } catch (Exception e) {
            throw new JoneException("Invalid datetime format. Please use yyyy-MM-dd HHmm.");
        }
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: "
                + start.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a"))
                + " to: "
                + end.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a")) + ")";
    }

    @Override
    public String toSaveFormat() {
        return "E | " + (done ? 1 : 0) + " | " + description
                + " | " + start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"))
                + " | " + end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
    }
}
