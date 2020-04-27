import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestHistory {
    // {testA: [methodA: 1, methodB:2], testB: [methodA: 1, methodB:2]}
    private Map<String, List<TestHistoryMethod>> tests = new HashMap<String, List<TestHistoryMethod>>();

    public void addTest(String testFileName, String methodName, boolean failed) {
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
                this.createNewMethod(test, methodName, failed);
            }
        } else {
            List<TestHistoryMethod> newList = new ArrayList<TestHistoryMethod>();

            this.createNewMethod(newList, methodName, failed);

            this.tests.put(testFileName, newList);
        }
    }


    public Map<String, List<TestHistoryMethod>> getTests() {
        return this.tests;
    }

    private void createNewMethod(List<TestHistoryMethod> newList, String methodName, boolean failed) {
        TestHistoryMethod newMethod = new TestHistoryMethod(methodName, failed);

        newMethod.increaseOccurance();

        newList.add(newMethod);
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
}
