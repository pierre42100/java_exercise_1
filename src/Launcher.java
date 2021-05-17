import java.util.Scanner;

public class Launcher {
    public static void main(String[] args) {
        System.out.println("Hello world");

        Scanner scanner = new Scanner(System.in);

        do {
            String instruction = scanner.nextLine();
            if ("quit".equals(instruction))
                break;

            if ("fibo".equals(instruction)) {
                System.out.println("Veuillez maintenant définir l'index de fibo que vous souhaitez obtenir :");
                System.out.println(fibo(scanner.nextInt()));
                scanner.nextLine();
                continue;
            }

            System.out.println("Unknown command");
        } while (true);
    }

    private static int fibo(int val) {
        return switch (val) {
            case 0 -> 0;
            case 1 -> 1;
            default -> fibo(val - 1) + fibo(val - 2);
        };
    }
}
