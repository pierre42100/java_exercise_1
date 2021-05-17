import java.util.Scanner;

public class Launcher {
    public static void main(String[] args) {
        System.out.println("Hello world");

        Scanner scanner = new Scanner(System.in);

        do {
            String instruction = scanner.next();
            if ("quit".equals(instruction))
                break;
            System.out.println("Unknown command");
        } while (true);
    }
}
