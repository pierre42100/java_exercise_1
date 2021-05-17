import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

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

            if ("freq".equals(instruction)) {
                System.out.println("Veuillez saisir le nom du fichier pour lequel vous voulez une analyse:");
                freq(scanner.nextLine());
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

    private static void freq(String filename) {
        String content;
        try {
            content = java.nio.file.Files.readString(Path.of(filename));
        } catch (IOException e) {
            System.err.println("Unreadable file: " + e.getMessage());
            return;
        }

        content = content.toLowerCase().replaceAll("[.!?\\-'\"]", " ");

        for (String line : content.split("\n")) {
            if (line.isBlank()) {
                System.out.println();
            }

            var intermediate = Arrays.stream(line.split(" "))
                    .filter(r -> !r.isBlank())
                    .collect(Collectors.groupingBy(g -> g));

            var res = new HashMap<String, Integer>();
            for (var i : intermediate.keySet()) {
                res.put(i, intermediate.get(i).size());
            }

            var keep_lengths = res.values().stream()
                    .sorted((a, b) -> Integer.compare(b, a))
                    .limit(3)
                    .collect(Collectors.toSet());

            List<String> words = new ArrayList<>();
            for (var e : keep_lengths.stream().sorted((a, b) -> Integer.compare(b, a)).toList()) {
                for (var k : res.keySet()) {
                    if (words.size() == 3)
                        break;

                    if (res.get(k).equals(e))
                        words.add(k);
                }
            }

            System.out.println(String.join(" ", words));
        }
    }
}
