import java.util.Scanner;

public class Launcher {
    public static void main(String[] args) {
        System.out.println("Hello world");

        Scanner scanner = new Scanner(System.in);
        String instruction = scanner.next();

        if (!"quit".equals(instruction))
            System.out.println("Unknown command");
    }
}
