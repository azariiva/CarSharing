import java.util.Scanner;
import java.util.stream.Stream;

class Main {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        var size = Integer.parseInt(scanner.nextLine().trim());
        var array = Stream.of(scanner.nextLine().trim().split("\\s"))
                .mapToInt(Integer::parseInt).toArray();
        var maximumProduct = -1;

        for (int i = 0; i < size - 1; i++) {
            var product = array[i] * array[i + 1];
            if (product > maximumProduct) {
                maximumProduct = product;
            }
        }
        System.out.println(maximumProduct);
    }
}