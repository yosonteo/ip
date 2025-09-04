public class Parser {
    public static String getCommandWord(String input) {
        return input.split(" ")[0];
    }

    public static String getArgs(String input) {
        int firstSpace = input.indexOf(" ");
        if (firstSpace == -1) {
            return "";
        }
        return input.substring(firstSpace + 1).trim();
    }
}
