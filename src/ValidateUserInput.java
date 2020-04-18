import java.util.regex.Pattern;

public class ValidateUserInput {
    private static final Pattern runAllTestsPattern = Pattern.compile("1.");
    private static final Pattern getMostFailingTest = Pattern.compile("3.");
    private static final Pattern shouldExit = Pattern.compile("exit");

    static boolean isRunAllTestsCommand(String s) {
        return runAllTestsPattern.matcher(s).lookingAt();
    }
    static boolean isGetMostFailingTest(String s) { return getMostFailingTest.matcher(s).lookingAt(); }
    static boolean isExitCommand(String s) { return shouldExit.matcher(s).lookingAt(); }
}