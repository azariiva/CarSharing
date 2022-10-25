import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

class Main {
    public static void main(String[] args) {
        var partsQty = new java.util.HashMap<>(Map.of(
                0, 0,
                1, 0,
                -1, 0
        ));

        Scanner scanner = new Scanner(System.in);
        var n = Integer.parseInt(scanner.nextLine().trim());
        Stream.generate(() -> Integer.parseInt(scanner.nextLine().trim()))
                .limit(n)
                .forEach(i -> partsQty.put(i, partsQty.get(i) + 1));
        System.out.printf("%d %d %d", partsQty.get(0), partsQty.get(1), partsQty.get(-1));
    }
}