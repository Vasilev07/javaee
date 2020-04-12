import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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

class TestedClass {
    private String gosho = "Gosho";

    public String someMethodThatReturnString() {
        // super logic, magic
        return this.gosho;
    }

    public int someSmartMethodThatReturnZero() {
        return 0;
    }
}

class TestExample {
    TestedClass mocked = new TestedClass();
    Assetions assertionLibrary = new Assetions();

    // should fail because we are returning Gosho from tested Class
    @Test
    void testA()
    {
        assertionLibrary.assertEquals("NotGosho", mocked.someMethodThatReturnString());
    }

    // should pass because we are returning Gosho from tested Class
    @Test
    void testB()
    {
        assertionLibrary.assertEquals("Gosho", mocked.someMethodThatReturnString());
    }

    // should be skipped
    @Test(skip = true)
    void testC()
    {

    }

    // should fail because we are expecting 1 to equal 0
    @Test
    void testSomeSmartMethodThatReturnZeroFail()
    {
        assertionLibrary.assertEquals(1, mocked.someSmartMethodThatReturnZero());
    }

    // should pass because we are expecting 0 to equal 0
    @Test
    void testSomeSmartMethodThatReturnZeroPass()
    {
        assertionLibrary.assertEquals(0, mocked.someSmartMethodThatReturnZero());
    }

    // should fail because passed falue is true
    @Test
    void testIfAssumeTrueFail()
    {
        assertionLibrary.assumeTrue(mocked.someMethodThatReturnString().contains("T"));
    }

    @Test
    void testIfAssumeTruePass()
    {
        assertionLibrary.assumeTrue(mocked.someMethodThatReturnString().contains("G"));
    }

    @Test
    void testIfFailFail()
    {
        assertionLibrary.assumeTrue(mocked.someMethodThatReturnString().contains("G"));
        assertionLibrary.fail("this will always fail");
    }

    @BeforeAll
    void beforeAllSection()
    {
        System.out.println("This should be run before test suite test");
    }

    @AfterAll
    void AfterAllSection()
    {
        System.out.println("This should be run after test suite test");
    }

    @BeforeEach
    void beforeEachSection()
    {
        System.out.println("This should be run before every test test");
    }

    @AfterEach
    void afterEachSection()
    {
        System.out.println("This should be run after every test test");
    }
}

class Assetions {
    public void assertEquals(int value, int methodResult) {
        if (value != methodResult) {
            throw new RuntimeException("Expected value is not equal to result");
        }
    }

    public void assertEquals(String value, String methodResult) {
        if (value != methodResult) {
            throw new RuntimeException("Expected value is not equal to result");
        }
    }

    public void assumeTrue(boolean value) {
        if (!value) {
            throw new RuntimeException("Expected value is not equal to true");
        }
    }

    public void fail(String errorMessage)
    {
        throw new RuntimeException(errorMessage);
    }
}

class RunTest {
    public static void main(String[] args) throws Exception {
        System.out.println("Testing...");
        int passed = 0;
        int failed = 0;
        int count = 0;
        int ignore = 0;

        TestExample testSuiteToRun = new TestExample();

        Class testSuiteToRunReflection = testSuiteToRun.getClass();
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
    }
}