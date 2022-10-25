import java.time.LocalDate;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        var dateString = scanner.nextLine().trim();
        var date = LocalDate.parse(dateString);
        System.out.printf("%d %d\n", date.getDayOfYear(), date.getDayOfMonth());
    }
}