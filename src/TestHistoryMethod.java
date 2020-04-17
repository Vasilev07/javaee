public class TestHistoryMethod {
    private String methodName = null;
    private int methodOccurance = 0;

    public TestHistoryMethod(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public void increaseOccurance() {
        this.methodOccurance++;
    }
}
