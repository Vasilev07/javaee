import java.util.HashMap;
import java.util.Map;

public class TestHistory {
    private static Map<String, TestHistoryMethod> failedTests = new HashMap<String, TestHistoryMethod>();

    public static void addFailedTest(String testFileName, String methodName) {
        Map<String, TestHistoryMethod> element = (Map<String, TestHistoryMethod>) checkIfTestWasRanInThePast(testFileName);
        boolean elementFound = element.isEmpty();
        if (elementFound){
        } else {
            failedTests.put(testFileName, new TestHistoryMethod(methodName));
        }
    }

    public static Map<String, TestHistoryMethod> getFailedTestsHistory() {
        return failedTests;
    }

    private static TestHistoryMethod checkIfTestWasRanInThePast(String testFileName) {
        return failedTests.get(testFileName.toString());
    }
}
