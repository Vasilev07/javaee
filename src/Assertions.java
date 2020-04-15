public class Assertions {
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
