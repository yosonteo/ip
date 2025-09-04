package jone;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    private static final String DATE_FORMAT_ERROR = "Invalid date format. Please use yyyy-MM-dd.";

    private final LocalDate by;

    public Deadline(String description, String by) throws JoneException {
        super(description);
        try {
            this.by = LocalDate.parse(by);
        } catch (DateTimeParseException e) {
            throw new JoneException(DATE_FORMAT_ERROR);
        }
    }

    public Deadline(String description, String by, boolean done) throws JoneException {
        super(description, done);
        try {
            this.by = LocalDate.parse(by);
        } catch (DateTimeParseException e) {
            throw new JoneException(DATE_FORMAT_ERROR);
        }
    }

    @Override
    public String toString() {
        return "[D]" + super.toString()
                + " (by: " + by.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }

    @Override
    public String toSaveFormat() {
        return "D | " + (isDone() ? 1 : 0) + " | " + getDescription() + " | " + by;
    }
}
