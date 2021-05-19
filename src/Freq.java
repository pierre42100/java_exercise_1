import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Freq implements Command {
    @Override
    public String name() {
        return "freq";
    }

    @Override
    public boolean run(Scanner sc) {
        System.out.println("Veuillez saisir le nom du fichier pour lequel vous voulez une analyse:");
        freq(sc.nextLine());
        return false;
    }

    private static void freq(String filename) {
        String content;
        try {
            content = java.nio.file.Files.readString(Path.of(filename));
        } catch (IOException e) {
            System.err.println("Unreadable file: " + e.getMessage());
            return;
        }

        content = content.toLowerCase().replaceAll("[.!?\\-'\"\n]", " ");

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
