package jone;

/**
 * Parses user input into command word and arguments, with validation.
 */
public class Parser {

    /**
     * Splits the raw user input into the command word and arguments.
     *
     * @param fullCommand The raw input string from the user.
     * @return A String array: [0] = command word, [1] = arguments (might be empty).
     * @throws JoneException If the input is invalid for known commands.
     */
    public static String[] parse(String fullCommand) throws JoneException {
        String[] parts = fullCommand.trim().split(" ", 2);
        String commandWord = parts[0].toLowerCase();
        String arguments = (parts.length > 1) ? parts[1].trim() : "";

        switch (commandWord) {
            case "todo":
                if (arguments.isEmpty()) {
                    throw new JoneException("Description of todo cannot be empty");
                }
                break;

            case "deadline":
                if (arguments.isEmpty() || !arguments.contains("/by")) {
                    throw new JoneException(
                            "Deadline must be in the format: deadline <desc> /by <yyyy-MM-dd>"
                    );
                }
                break;

            case "event":
                if (arguments.isEmpty() || !arguments.contains("/from") || !arguments.contains("/to")) {
                    throw new JoneException(
                            "Event must be in the format: event <desc> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>"
                    );
                }
                break;

            case "list":
            case "mark":
            case "unmark":
            case "delete":
                // handled in Jone
                break;

            case "find":
                if (arguments.isEmpty()) {
                    throw new JoneException("Please provide a keyword to search for");
                }
                break;

            case "bye":
                // Allow and accept 'bye'
                break;

            default:
                throw new JoneException("I'm sorry, but I don't know what that means");
        }

        return new String[]{commandWord, arguments};
    }
}
