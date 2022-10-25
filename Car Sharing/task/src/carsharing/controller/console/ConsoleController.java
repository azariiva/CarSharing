package carsharing.controller.console;

import carsharing.controller.AppState;

public class ConsoleController implements Runnable {

    @Override
    public void run() {
        AppState state = new ConsoleMainMenu();
        while (state != null) {
            state = state.exec();
        }
    }
}
