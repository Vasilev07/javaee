import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Test {
    public boolean skip() default false;
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface BeforeAll {
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface AfterAll {
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface BeforeEach {
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface AfterEach {
}

public class RunTest {
    public static void main(String[] args) throws Exception {
        // output to the console the commands that we are expecting
        UserInstructions.runTestByGivenName();

        // we are expecting exit input in order to break this loop
        while (true) {
            try {
                runUserInput();
            } catch (Exception e) {
                break;
            }
        }

    }

    private static void runUserInput() throws Exception {
        Scanner in = new Scanner(System.in);
        String userInput = in.nextLine();

        if (ValidateUserInput.isRunAllTestsCommand(userInput)) {
            String testNameToRun = userInput.substring(2);
            runAllTestsForGivenClass(testNameToRun);
        } else if(ValidateUserInput.isGetMostFailingTest(userInput)) {
            TestInformation currentTestInformation = FailedTestHistory.getMostFailingTest();
            System.out.println(currentTestInformation.getMethodName() + ": " + currentTestInformation.getOccurances());
        } else if (ValidateUserInput.isExitCommand(userInput)) {
            throw new Exception("should exit");
        }
    }

    private static void runAllTestsForGivenClass(String classNameToRun) throws Exception {
        System.out.println("Testing...");
        int passed = 0;
        int failed = 0;
        int count = 0;
        int ignore = 0;
        try {
            Class ClassTests = Class.forName(classNameToRun);
            Object ClassTestsToRun = ClassTests.newInstance();

            Class testSuiteToRunReflection = ClassTestsToRun.getClass();
            // check for before each and after each annotations
            Method beforeAllMethod = null;
            boolean shouldRunBeforeAllMethod = false;
            Method afterAllMethod = null;
            boolean shouldRunAfterAllMethod = false;

            Method beforeEachMethod = null;
            boolean shouldRunBeforeEachMethod = false;
            Method afterEachMethod = null;
            boolean shouldRunAfterEachMethod = false;

            for (Method method : testSuiteToRunReflection.getDeclaredMethods())
            {
                if (method.isAnnotationPresent(BeforeAll.class)) {
                    shouldRunBeforeAllMethod = true;
                    beforeAllMethod = method;
                } else if(method.isAnnotationPresent(AfterAll.class)) {
                    shouldRunAfterAllMethod = true;
                    afterAllMethod = method;
                } else if(method.isAnnotationPresent(BeforeEach.class)){
                    shouldRunBeforeEachMethod = true;
                    beforeEachMethod = method;
                } else if(method.isAnnotationPresent(AfterEach.class)) {
                    shouldRunAfterEachMethod = true;
                    afterEachMethod = method;
                }
            }

            // iterate over class methods
            for (int i = 0; i < testSuiteToRunReflection.getDeclaredMethods().length; i++) {
                // check if it is first run and should run BeforeAll method
                if (i == 0 && shouldRunBeforeAllMethod && beforeAllMethod != null)
                {
                    beforeAllMethod.invoke(testSuiteToRunReflection.newInstance());
                }

                Method method = testSuiteToRunReflection.getDeclaredMethods()[i];

                if (method.isAnnotationPresent(Test.class)) {
                    Annotation testAnnotation = method.getAnnotation(Test.class);

                    Test test = (Test) testAnnotation;

                    if (!test.skip()) {
                        if (shouldRunBeforeEachMethod && beforeEachMethod != null) {
                            beforeEachMethod.invoke(testSuiteToRunReflection.newInstance());
                        }

                        try {
                            method.invoke(testSuiteToRunReflection.newInstance());
                            System.out.printf("%s - Test '%s' - passed %n", ++count, method.getName());
                            passed++;
                        } catch (Throwable ex) {
                            System.out.printf("%s - Test '%s' - failed: %s %n", ++count, method.getName(), ex.getCause());
                            failed++;
                            FailedTestHistory.addFailingTest(classNameToRun, method.getName());
                        }

                        if (shouldRunAfterEachMethod && afterEachMethod != null) {
                            afterEachMethod.invoke(testSuiteToRunReflection.newInstance());
                        }
                    } else {
                        ignore++;
                    }
                }
                if (i == testSuiteToRunReflection.getDeclaredMethods().length - 1) {

                    if (shouldRunAfterAllMethod && afterAllMethod != null)
                    {
                        afterAllMethod.invoke(testSuiteToRunReflection.newInstance());
                    }

                    System.out.printf("%nResult : Total : %d, Passed: %d, Failed %d, Ignore %d%n", count, passed, failed, ignore);
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Class with name: " + classNameToRun + " not found.");
        }
    }
}