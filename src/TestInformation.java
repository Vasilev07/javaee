public class TestInformation {
    private String methodName;
    private int occurances;

    private TestInformation(String methodName, int occurances) {
        this.methodName = methodName;
        this.occurances = occurances;
    }

    public static TestInformation getTestInformation(String methodName, int occurances) {
        return new TestInformation(methodName, occurances);
    }

    public String getMethodName() {
        return this.methodName;
    }

    public int getOccurances() {
        return this.occurances;
    }
}
