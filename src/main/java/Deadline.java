import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDate by;

    public Deadline(String description, String by) throws JoneException {
        super(description);
        try {
            this.by = LocalDate.parse(by);
        } catch (Exception e) {
            throw new JoneException("Invalid date format. Please use yyyy-MM-dd.");
        }
    }

    public Deadline(String description, String by, boolean done) throws JoneException {
        super(description, done);
        try {
            this.by = LocalDate.parse(by);
        } catch (Exception e) {
            throw new JoneException("Invalid date format. Please use yyyy-MM-dd.");
        }
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }

    @Override
    public String toSaveFormat() {
        return "D | " + (done ? 1 : 0) + " | " + description + " | " + by;
    }
}
