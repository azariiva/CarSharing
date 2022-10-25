package carsharing.controller.console;

import carsharing.controller.AppState;
import carsharing.controller.Menu;

import java.util.Arrays;
import java.util.Map;

public abstract class ConsoleMenu implements Menu {

    private final String[] menuOptionsList;
    private final Map<Integer, AppState> menuOptionTransitions;

    protected ConsoleMenu(String[] menuOptionsList, Map<Integer, AppState> menuOptionTransitions) {
        this.menuOptionsList = menuOptionsList;
        this.menuOptionTransitions = menuOptionTransitions;
    }

    @Override
    public void showMenu() {
        Arrays.stream(menuOptionsList).forEach(System.out::println);
    }

    @Override
    public AppState handleUserInput() {
        var userOption = Integer.parseInt(SysoutScannerContainer.getScanner().nextLine().trim());
        System.out.println();
        return menuOptionTransitions.getOrDefault(userOption, () -> this);
    }
}
