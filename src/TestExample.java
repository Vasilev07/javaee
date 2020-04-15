public class TestExample {
    TestedClass mocked = new TestedClass();
    Assertions assertionLibrary = new Assertions();

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
