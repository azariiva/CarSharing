import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Main {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        var d = Integer.parseInt(scanner.nextLine().trim());
        var knownWords = Stream.generate(scanner::nextLine)
                .map(String::trim)
                .map(String::toLowerCase)
                .limit(d)
                .collect(Collectors.toUnmodifiableSet());
        var l = Integer.parseInt(scanner.nextLine().trim());
        var textWords = Stream.generate(scanner::nextLine)
                .limit(l)
                .flatMap(s -> Stream.of(s.split("\\s")))
                .map(String::trim)
                .collect(Collectors.toSet());
        textWords.removeIf(w -> knownWords.contains(w.toLowerCase()));
        for (String textWord : textWords) {
            System.out.println(textWord);
        }
    }
}