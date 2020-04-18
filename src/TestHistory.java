import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestHistory {
    private static Map<String, List<TestHistoryMethod>> failedTests = new HashMap<String, List<TestHistoryMethod>>();

    public static void addFailedTest(String testFileName, String methodName) {
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
                createNewMetod(test, methodName);
            }
        } else {
            List<TestHistoryMethod> newList = new ArrayList<TestHistoryMethod>();

            createNewMetod(newList, methodName);

            failedTests.put(testFileName, newList);
        }
    }

    public static Map<String, List<TestHistoryMethod>> getFailedTestsHistory() {
        return failedTests;
    }

    private static void createNewMetod(List<TestHistoryMethod> newList, String methodName) {
        TestHistoryMethod newMethod = new TestHistoryMethod(methodName);

        newMethod.increaseOccurance();

        newList.add(newMethod);
    }

    private static boolean checkIfTestWasRanInThePast(String testFileName) {
        return failedTests.containsKey(testFileName.toString());
    }

    private static List<TestHistoryMethod> getTest(String testFileName) {
        return failedTests.get(testFileName);
    }

    private static int getTestMethodIndex(String testFileName, TestHistoryMethod method) {
        return getTest(testFileName).indexOf(method);
    }


    private static TestHistoryMethod getTestMethod(String testFileName, String methodName) {
        TestHistoryMethod method = null;
        List<TestHistoryMethod> items = failedTests.get(testFileName);

        for(TestHistoryMethod item : items){
            if(item.getMethodName() == methodName) {
                method = item;
            }
        }

        return method;
    }

    private static boolean checkIfTestMethodWasRanInThePast(String testFileName, String methodName) {
        List<TestHistoryMethod> items = failedTests.get(testFileName);
        boolean exist = false;

        for(TestHistoryMethod item : items){
            if(item.getMethodName() == methodName) {
                exist = true;
            }
        }

        return exist;
    }
}
