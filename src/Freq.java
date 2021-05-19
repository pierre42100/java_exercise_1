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

        Map<String, Integer> freq = new HashMap<>();

        for (var word : content.split(" ")) {
            if (word.isBlank()) continue;

            freq.putIfAbsent(word, 0);
            freq.put(word, freq.get(word) + 1);
        }

        List<String> words = new ArrayList<>();

        while(words.size() < 3 && freq.keySet().size() > 0) {
            int max = Collections.max(freq.values());
            var keys = freq.keySet().stream().filter(k -> freq.get(k) == max).collect(Collectors.toList());
            var last = keys.get(keys.size() - 1);
            words.add(last);
            freq.remove(last);
        }

        System.out.println(String.join(" ", words));
    }
}
