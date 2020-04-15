public class TestHistoryMethod {
    private String methodName = null;
    private int methodOccurance = 0;

    public TestHistoryMethod(String methodName) {
        this.methodName = methodName;
    }

    public void increaseOccurance() {
        this.methodOccurance++;
    }
}
