public class SecondTestExample {
    SecondTestedClass mocked = new SecondTestedClass();
    Assertions assertionLibrary = new Assertions();

    @Test
    void testSomeSmartMethodThatReturnZeroFail2()
    {
        assertionLibrary.assertEquals(1, mocked.someSmartMethodThatReturnZero2());
    }
}
