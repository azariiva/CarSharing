package carsharing.controller;

@FunctionalInterface
public interface AppState {

    AppState exec();
}
