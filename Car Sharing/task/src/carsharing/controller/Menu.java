package carsharing.controller;

public interface Menu extends AppState {

    void showMenu();

    AppState handleUserInput();

    @Override
    default AppState exec() {
        showMenu();
        return handleUserInput();
    }
}
