import java.util.Scanner;

public class Fibo implements Command {
    @Override
    public String name() {
        return "fibo";
    }

    @Override
    public boolean run(Scanner sc) {
        System.out.println("Veuillez maintenant définir l'index de fibo que vous souhaitez obtenir :");
        System.out.println(fibo(sc.nextInt()));
        sc.nextLine();

        return false;
    }

    private static int fibo(int val) {
        return switch (val) {
            case 0 -> 0;
            case 1 -> 1;
            default -> fibo(val - 1) + fibo(val - 2);
        };
    }
}
