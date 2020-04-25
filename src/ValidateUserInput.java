import java.util.regex.Pattern;

public class ValidateUserInput {
    // TODO: might think of a base class and change the structure of this one
    private static final Pattern runAllTestsPattern = Pattern.compile("1.");
    private static final Pattern getHistory = Pattern.compile("2.");
    private static final Pattern getMostFailingTest = Pattern.compile("3.");
    private static final Pattern getMostPassingTest = Pattern.compile("4.");
    private static final Pattern shouldExit = Pattern.compile("exit");

    static boolean isRunAllTestsCommand(String s) {
        return runAllTestsPattern.matcher(s).lookingAt();
    }
    static boolean isHistoryCommand(String s) {
        return getHistory.matcher(s).lookingAt();
    }
    static boolean isGetMostFailingTest(String s) { return getMostFailingTest.matcher(s).lookingAt(); }
    static boolean isGetMostPassingTest(String s) {return getMostPassingTest.matcher(s).lookingAt();}
    static boolean isExitCommand(String s) { return shouldExit.matcher(s).lookingAt(); }
}