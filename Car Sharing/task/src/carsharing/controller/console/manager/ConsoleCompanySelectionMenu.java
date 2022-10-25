package carsharing.controller.console.manager;

import carsharing.controller.AppState;
import carsharing.controller.console.ConsoleMenu;
import carsharing.controller.console.ConsoleUtils;
import carsharing.persistance.repository.JdbcCompanyRepository;

import java.util.AbstractMap;
import java.util.Map;

public class ConsoleCompanySelectionMenu extends ConsoleMenu {

    public static AppState getConsoleCompanyListMenu() {
        final var companies = JdbcCompanyRepository.getInstance().readCompanies();

        if (companies.isEmpty()) {
            return () -> {
                System.out.println("The company list is empty!");
                System.out.println();
                return new ConsoleManagerMenu();
            };
        }

        final var menuOptionsList = ConsoleUtils.generateMenuOptionsList(
                companies,
                (idx) -> companies.get(idx).getName(),
                "0. Back"
        );
        final var menuOptionTransitions = ConsoleUtils.generateMenuOptionTransitions(
                companies,
                idx -> () -> {
                    var company = companies.get(idx);
                    System.out.printf("'%s' company\n", company.getName());
                    return new ConsoleCompanyMenu(company.getId());
                },
                new AbstractMap.SimpleImmutableEntry<Integer, AppState>(0, ConsoleManagerMenu::new)
        );
        return new ConsoleCompanySelectionMenu(menuOptionsList, menuOptionTransitions);
    }

    private ConsoleCompanySelectionMenu(String[] menuOptionsList, Map<Integer, AppState> menuOptionTransitions) {
        super(menuOptionsList, menuOptionTransitions);
    }

    @Override
    public void showMenu() {
        System.out.println("Choose the company:");
        super.showMenu();
    }
}
