import java.util.List;
import java.util.Map;

public class FailedTestHistory extends TestHistory {
    public static void addFailingTest(String testFileName, String methodName) {
        TestHistory.addTest(testFileName, methodName);
    }


    public static TestInformation getMostFailingTest() {
        Map<String, List<TestHistoryMethod>> testsHistory = TestHistory.getTestsHistory();
        String mostFailedTestName = null;
        int mostFailedTestTimes = 0;

        for (Map.Entry<String, List<TestHistoryMethod>> entry : testsHistory.entrySet()) {
            List<TestHistoryMethod> testHistoryMethods = entry.getValue();

            for (TestHistoryMethod testHistoryMethod: testHistoryMethods) {
                if (testHistoryMethod.getMethodOccurance() > mostFailedTestTimes) {
                    mostFailedTestTimes = testHistoryMethod.getMethodOccurance();
                    mostFailedTestName = testHistoryMethod.getMethodName();
                }
            }
        }

        return TestInformation.getTestInformation(mostFailedTestName, mostFailedTestTimes);
    }
}
