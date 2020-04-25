import java.util.List;
import java.util.Map;

public class RunCommands {

    public void runCommand(String userInput)throws Exception {
//        if (ValidateUserInput.isRunAllTestsCommand(userInput)) {
//            // skipping the number of the command
//            String testNameToRun = userInput.substring(2);
//            RunTest.runAllTestsForGivenClass(testNameToRun);
//        }else if (ValidateUserInput.isHistoryCommand(userInput)){
//            Map<String, List<TestHistoryMethod>> allTests = TestHistory.getTests();
//            for (Map.Entry<String, List<TestHistoryMethod>> entry : allTests.entrySet()) {
//                List<TestHistoryMethod> testHistoryMethods = entry.getValue();
//                System.out.println("_________________");
//                System.out.println("test name: " + entry.getKey());
//                for (TestHistoryMethod testHistoryMethod: testHistoryMethods) {
//                    System.out.println("method name: " + testHistoryMethod.getMethodName());
//                    System.out.println("method name occurance: " + testHistoryMethod.getMethodOccurance());
//                    System.out.println("method failed: " + testHistoryMethod.isFailed());
//                    System.out.println("_________________");
//                }
//            }
//
//        } else if(ValidateUserInput.isGetMostFailingTest(userInput)) {
//            TestInformation currentTestInformation = FailedTestHistory.getMostFailingTest();
//            System.out.println(currentTestInformation.getMethodName() + ": " + currentTestInformation.getOccurances());
//        } else if (ValidateUserInput.isGetMostPassingTest(userInput)) {
//            TestInformation currentTestInformation = PassedTestHistory.getMostPassingTest();
//            System.out.println(currentTestInformation.getMethodName() + ": " + currentTestInformation.getOccurances());
//        } else if (ValidateUserInput.isExitCommand(userInput)) {
//            throw new Exception("should exit");
//        }
    }
}
