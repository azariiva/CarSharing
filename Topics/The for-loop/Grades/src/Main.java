import java.util.*;
import java.util.stream.IntStream;

class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final int n = Integer.parseInt(scanner.nextLine().trim());
        final Map<String, Integer> gradeCtr = new HashMap<>(Map.of(
                "A", 0,
                "B", 0,
                "C", 0,
                "D", 0
        ));

        IntStream.range(0, n)
                .mapToObj(unused -> scanner.nextLine())
                .map(String::trim)
                .forEach(grade -> gradeCtr.compute(grade, (k, v) -> {
                    if (v == null) {
                        throw new RuntimeException(String.format("Illegal grade %s", k));
                    }
                    return v + 1;
                }));
        final List<String> sortedGradeCtrEntryList = gradeCtr.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .map(Map.Entry::getValue)
                .map(Object::toString)
                .toList();
        System.out.println(String.join(" ", sortedGradeCtrEntryList));
    }
}