package carsharing.controller.console;

import carsharing.businesslayer.Customer;
import carsharing.controller.AppState;
import carsharing.controller.console.customer.ConsoleCustomerSelectionMenu;
import carsharing.controller.console.manager.ConsoleManagerMenu;
import carsharing.persistance.repository.JdbcCustomerRepository;

import java.util.Map;

public class ConsoleMainMenu extends ConsoleMenu {

    private static final String[] MENU_OPTIONS_LIST = {
            "1. Log in as a manager",
            "2. Log in as a customer",
            "3. Create a customer",
            "0. Exit"
    };

    public ConsoleMainMenu() {
        super(MENU_OPTIONS_LIST, Map.of(
                0, () -> null,
                1, ConsoleManagerMenu::new,
                2, ConsoleCustomerSelectionMenu::getConsoleCustomerListMenu,
                3, ConsoleMainMenu::handleCreateCustomerOption
        ));
    }

    private static AppState handleCreateCustomerOption() {
        var customer = new Customer();
        customer.setName(askCustomerName());
        JdbcCustomerRepository.getInstance().createCustomer(customer);
        System.out.println("The customer was added!");
        System.out.println();
        return new ConsoleMainMenu();
    }

    private static String askCustomerName() {
        System.out.println("Enter the customer name:");
        return SysoutScannerContainer.getScanner().nextLine().trim();
    }
}
