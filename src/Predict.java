import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Predict implements Command {
    private class Word {
        private final String word;
        private final Map<String, Integer> occurences = new HashMap<>();

        public Word(String word) {
            this.word = word;
        }

        public void putFollower(String w) {
            this.occurences.put(w, this.occurences.getOrDefault(w, 0) + 1);
        }

        public String predict() {
            if (occurences.isEmpty())
                return null;
            var i = occurences.values()
                    .stream()
                    .sorted((a, b) -> Integer.compare(b, a))
                    .limit(1)
                    .findFirst();

            if (i.isEmpty())
                throw new RuntimeException();

            for (var k : this.occurences.keySet()) {
                if (this.occurences.get(k).equals(i.get()))
                    return k;
            }

            return null;
        }
    }

    @Override
    public String name() {
        return "predict";
    }

    @Override
    public boolean run(Scanner sc) {
        System.out.println("Veuillez saisir le nom du fichier pour lequel vous voulez une analyse:");
        String content;
        try {
            content = Files.readString(Path.of(sc.nextLine()));
        } catch (Exception e) {
            System.err.println("Unreadable file: " + e.getMessage());
            return false;
        }

        content = content.toLowerCase()
                .replaceAll("[.!?\\-'\"\t\n]", " ")
                .replaceAll(" {2}", " ");

        if (content.isBlank())
            return false;

        Map<String, Word> words = new HashMap<>();

        var lastWord = Arrays.stream(content.split(" "))
                .filter(s -> !s.isBlank())
                .reduce("", (prev, next) -> {
                    if (!prev.isBlank()) {
                        words.putIfAbsent(prev, new Word(prev));
                        words.get(prev).putFollower(next);
                    }
                    return next;
                });

        words.putIfAbsent(lastWord, new Word(lastWord));


        System.out.println("Veuillez maintenant entrer un mot :");
        var startWord = sc.nextLine().toLowerCase();

        if(!words.containsKey(startWord))
            System.err.println("Le mot n'est pas présent dans le texte !");

        else {
            var sentence = new ArrayList<>(List.of(startWord));
            while(sentence.size()<20) {
                var nextWord = words.get(sentence.get(sentence.size()-1)).predict();
                if(nextWord == null)
                    break;
                sentence.add(nextWord);
            }

            System.out.println(String.join(" ", sentence));
        }


        return false;
    }
}
