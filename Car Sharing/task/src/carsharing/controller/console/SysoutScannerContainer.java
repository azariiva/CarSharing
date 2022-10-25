package carsharing.controller.console;

import java.util.Scanner;

public class SysoutScannerContainer {

    private static Scanner scanner;

    public static Scanner getScanner() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        return scanner;
    }
}
