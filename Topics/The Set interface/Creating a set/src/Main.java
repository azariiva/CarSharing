import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) {
        Set<String> set = new TreeSet<>(List.of("Gamma", "Alpha", "Omega"));
        System.out.println(set);
    }
}