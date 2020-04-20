import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestHistory {
    // {testA: [methodA: 1, methodB:2], testB: [methodA: 1, methodB:2]}
    private static Map<String, List<TestHistoryMethod>> tests = new HashMap<String, List<TestHistoryMethod>>();

    public static void addTest(String testFileName, String methodName) {
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
                createNewMethod(test, methodName);
            }
        } else {
            List<TestHistoryMethod> newList = new ArrayList<TestHistoryMethod>();

            createNewMethod(newList, methodName);

            tests.put(testFileName, newList);
        }
    }


    public static Map<String, List<TestHistoryMethod>> getTests() {
        return tests;
    }

    static Map<String, List<TestHistoryMethod>> getTestsHistory() {
        return tests;
    }

    private static void createNewMethod(List<TestHistoryMethod> newList, String methodName) {
        TestHistoryMethod newMethod = new TestHistoryMethod(methodName);

        newMethod.increaseOccurance();

        newList.add(newMethod);
    }

    private static boolean checkIfTestWasRanInThePast(String testFileName) {
        return tests.containsKey(testFileName.toString());
    }

    private static List<TestHistoryMethod> getTest(String testFileName) {
        return tests.get(testFileName);
    }

    private static int getTestMethodIndex(String testFileName, TestHistoryMethod method) {
        return getTest(testFileName).indexOf(method);
    }

    private static TestHistoryMethod getTestMethod(String testFileName, String methodName) {
        TestHistoryMethod method = null;
        List<TestHistoryMethod> items = tests.get(testFileName);

        for(TestHistoryMethod item : items){
            if(item.getMethodName() == methodName) {
                method = item;
            }
        }

        return method;
    }

    private static boolean checkIfTestMethodWasRanInThePast(String testFileName, String methodName) {
        List<TestHistoryMethod> items = tests.get(testFileName);
        boolean exist = false;

        for(TestHistoryMethod item : items){
            if(item.getMethodName() == methodName) {
                exist = true;
            }
        }

        return exist;
    }
}
