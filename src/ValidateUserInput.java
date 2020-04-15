import java.util.regex.Pattern;

public class ValidateUserInput {
    private static final Pattern runAllTestsPattern = Pattern.compile("1.");

    static boolean isRunAllTestsCommand(String s) {
        return runAllTestsPattern.matcher(s).lookingAt();
    }
}
