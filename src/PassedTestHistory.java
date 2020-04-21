import java.util.List;
import java.util.Map;

public class PassedTestHistory extends TestHistory {
    public static void addPassingTest(String testFileName, String methodName) {
        TestHistory.addTest(testFileName, methodName, false);
    }

    public static TestInformation getMostPassingTest() {
        Map<String, List<TestHistoryMethod>> testsHistory = TestHistory.getTestsHistory();
        String mostPassingTestName = null;
        int mostPassingTestTimes = 0;

        for (Map.Entry<String, List<TestHistoryMethod>> entry : testsHistory.entrySet()) {
            List<TestHistoryMethod> testHistoryMethods = entry.getValue();

            for (TestHistoryMethod testHistoryMethod: testHistoryMethods) {
                if (testHistoryMethod.getMethodOccurance() > mostPassingTestTimes && !testHistoryMethod.isFailed()) {
                    mostPassingTestTimes = testHistoryMethod.getMethodOccurance();
                    mostPassingTestName = testHistoryMethod.getMethodName();
                }
            }
        }

        return TestInformation.getTestInformation(mostPassingTestName, mostPassingTestTimes);
    }
}
