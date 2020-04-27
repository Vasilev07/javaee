import java.util.List;
import java.util.Map;

public class FailedTestHistory {


    public TestInformation getMostFailingTest(Map<String, List<TestHistoryMethod>> tests) {
        Map<String, List<TestHistoryMethod>> testsHistory = tests;
        String mostFailedTestName = null;
        int mostFailedTestTimes = 0;

        for (Map.Entry<String, List<TestHistoryMethod>> entry : testsHistory.entrySet()) {
            List<TestHistoryMethod> testHistoryMethods = entry.getValue();

            for (TestHistoryMethod testHistoryMethod: testHistoryMethods) {
                if (testHistoryMethod.getMethodOccurance() > mostFailedTestTimes && testHistoryMethod.isFailed()) {
                    mostFailedTestTimes = testHistoryMethod.getMethodOccurance();
                    mostFailedTestName = testHistoryMethod.getMethodName();
                }
            }
        }

        return TestInformation.getTestInformation(mostFailedTestName, mostFailedTestTimes);
    }
}
