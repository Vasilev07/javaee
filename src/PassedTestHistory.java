import java.util.List;
import java.util.Map;

public class PassedTestHistory extends TestHistory {
    public TestInformation getMostPassingTest(Map<String, List<TestHistoryMethod>> tests) {
        Map<String, List<TestHistoryMethod>> testsHistory = tests;
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
