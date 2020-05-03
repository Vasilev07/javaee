import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestHistory {
    // {testA: [methodA: 1, methodB:2], testB: [methodA: 1, methodB:2]}
    private Map<String, List<TestHistoryMethod>> tests = new HashMap<String, List<TestHistoryMethod>>();

    public void addTest(String testFileName, String methodName, boolean failed) {
        // try to think of optimisation
        boolean isTestRanBefore = checkIfTestWasRanInThePast(testFileName);

        if (isTestRanBefore){
            boolean isMethodRanBefore = checkIfTestMethodWasRanInThePast(testFileName, methodName);
            List<TestHistoryMethod> test = getTest(testFileName);

            if (isMethodRanBefore) {
                TestHistoryMethod testMethod = getTestMethod(testFileName, methodName);
                testMethod.increaseOccurance();
                int testMethodIndex = getTestMethodIndex(testFileName, testMethod);

                test.set(testMethodIndex, testMethod);
            } else {
                TestHistoryMethod newMethod = this.createNewMethod(methodName, failed);
                newMethod.increaseOccurance();
                test.add(newMethod);
            }
        } else {
            List<TestHistoryMethod> newList = new ArrayList<TestHistoryMethod>();

            TestHistoryMethod newMethod = this.createNewMethod(methodName, failed);
            newMethod.increaseOccurance();
            newList.add(newMethod);

            this.tests.put(testFileName, newList);
        }
    }


    public Map<String, List<TestHistoryMethod>> getTests() {
        return this.tests;
    }

    private TestHistoryMethod createNewMethod(String methodName, boolean failed) {
        return new TestHistoryMethod(methodName, failed);
    }

    private boolean checkIfTestWasRanInThePast(String testFileName) {
        return this.tests.containsKey(testFileName.toString());
    }

    private List<TestHistoryMethod> getTest(String testFileName) {
        return this.tests.get(testFileName);
    }

    private int getTestMethodIndex(String testFileName, TestHistoryMethod method) {
        return this.getTest(testFileName).indexOf(method);
    }

    private TestHistoryMethod getTestMethod(String testFileName, String methodName) {
        TestHistoryMethod method = null;
        List<TestHistoryMethod> items = this.tests.get(testFileName);

        for(TestHistoryMethod item : items){
            if(item.getMethodName() == methodName) {
                method = item;
            }
        }

        return method;
    }

    private boolean checkIfTestMethodWasRanInThePast(String testFileName, String methodName) {
        List<TestHistoryMethod> items = this.tests.get(testFileName);
        boolean exist = false;

        for(TestHistoryMethod item : items){
            if(item.getMethodName() == methodName) {
                exist = true;
            }
        }

        return exist;
    }

    public TestInformation getMostFailingOrPassing(Map<String, List<TestHistoryMethod>> tests, boolean passing) {
        Map<String, List<TestHistoryMethod>> testsHistory = tests;
        String mostPassingOrFailingTestName = null;
        int mostPassingOrFailingTestTimes = 0;

        for (Map.Entry<String, List<TestHistoryMethod>> entry : testsHistory.entrySet()) {
            List<TestHistoryMethod> testHistoryMethods = entry.getValue();

            for (TestHistoryMethod testHistoryMethod : testHistoryMethods) {
                if(!passing) {
                    if (testHistoryMethod.getMethodOccurance() > mostPassingOrFailingTestTimes && !testHistoryMethod.isFailed()) {
                        mostPassingOrFailingTestTimes = testHistoryMethod.getMethodOccurance();
                        mostPassingOrFailingTestName = testHistoryMethod.getMethodName();
                    }
                } else {
                    if (testHistoryMethod.getMethodOccurance() > mostPassingOrFailingTestTimes && testHistoryMethod.isFailed()) {
                        mostPassingOrFailingTestTimes = testHistoryMethod.getMethodOccurance();
                        mostPassingOrFailingTestName = testHistoryMethod.getMethodName();
                    }
                }
            }
        }

        return TestInformation.getTestInformation(mostPassingOrFailingTestName, mostPassingOrFailingTestTimes);
    }
}
