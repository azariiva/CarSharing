import java.io.File;
import java.util.Objects;

class Siblings {

    public static boolean areSiblings(File f1, File f2) {
        return Objects.equals(f1.getParent(), f2.getParent());
    }
}