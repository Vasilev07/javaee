import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UserInput {
    private Scanner in = new Scanner(System.in);
    private String userInput;

    public void read() {
        userInput = in.nextLine();
    }

    public String get() {
        return userInput;
    }

}
