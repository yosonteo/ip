package jone;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HHmm";
    private static final String DATETIME_FORMAT_ERROR =
            "Invalid datetime format. Please use yyyy-MM-dd HHmm.";

    private final LocalDateTime start;
    private final LocalDateTime end;

    public Event(String description, String start, String end) throws JoneException {
        super(description);
        try {
            this.start = LocalDateTime.parse(start,
                    DateTimeFormatter.ofPattern(DATETIME_FORMAT));
            this.end = LocalDateTime.parse(end,
                    DateTimeFormatter.ofPattern(DATETIME_FORMAT));
        } catch (DateTimeParseException e) {
            throw new JoneException(DATETIME_FORMAT_ERROR);
        }
    }

    public Event(String description, String start, String end, boolean done) throws JoneException {
        super(description, done);
        try {
            this.start = LocalDateTime.parse(start,
                    DateTimeFormatter.ofPattern(DATETIME_FORMAT));
            this.end = LocalDateTime.parse(end,
                    DateTimeFormatter.ofPattern(DATETIME_FORMAT));
        } catch (DateTimeParseException e) {
            throw new JoneException(DATETIME_FORMAT_ERROR);
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
        return "E | " + (isDone() ? 1 : 0) + " | " + getDescription()
                + " | " + start.format(DateTimeFormatter.ofPattern(DATETIME_FORMAT))
                + " | " + end.format(DateTimeFormatter.ofPattern(DATETIME_FORMAT));
    }
}
