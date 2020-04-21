public class TestHistoryMethod {
    private String methodName = null;
    private int methodOccurance = 0;
    private boolean failed = false;
    public TestHistoryMethod(String methodName, boolean failed) {
        this.methodName = methodName;
        this.failed = failed;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public int getMethodOccurance() { return this.methodOccurance; }

    public boolean isFailed() { return this.failed; }

    public void increaseOccurance() {
        this.methodOccurance++;
    }
}
