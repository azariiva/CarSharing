package carsharing.controller.console.customer;

import carsharing.businesslayer.Customer;
import carsharing.controller.AppState;
import carsharing.controller.console.ConsoleMainMenu;
import carsharing.controller.console.ConsoleMenu;
import carsharing.persistance.repository.JdbcCarRepository;
import carsharing.persistance.repository.JdbcCompanyRepository;
import carsharing.persistance.repository.JdbcCustomerRepository;

import java.util.Map;

public class ConsoleCustomerMenu extends ConsoleMenu {

    private static final String[] MENU_OPTIONS_LIST = {
            "1. Rent a car",
            "2. Return a rented car",
            "3. My rented car",
            "0. Back"
    };

    protected ConsoleCustomerMenu(Customer customer) {
        super(MENU_OPTIONS_LIST, Map.of(
                0, ConsoleMainMenu::new,
                1, () -> handleRentCarOption(customer),
                2, () -> handleReturnRentedCarOption(customer),
                3, () -> handleMyRentedCarOption(customer)
                )
        );
    }

    private static AppState handleRentCarOption(Customer customer) {
        if (customer.getRentedCarId() != 0) {
            System.out.println("You've already rented a car!");
            System.out.println();
            return new ConsoleCustomerMenu(customer);
        }
        return ConsoleRentCarMenu.ConsoleRentCarMenuCompanySelection.getConsoleRentCarMenuCompanySelection(customer);
    }

    private static AppState handleReturnRentedCarOption(Customer customer) {
        if (customer.getRentedCarId() == 0) {
            System.out.println("You didn't rent a car!");
            System.out.println();
            return new ConsoleCustomerMenu(customer);
        }
        System.out.println("You've returned a rented car!");
        System.out.println();
        customer.setRentedCarId(0);
        JdbcCustomerRepository.getInstance().updateCustomer(customer);
        return new ConsoleCustomerMenu(customer);
    }

    private static AppState handleMyRentedCarOption(Customer customer) {
        if (customer.getRentedCarId() == 0) {
            System.out.println("You didn't rent a car!");
            System.out.println();
            return new ConsoleCustomerMenu(customer);
        }
        var car = JdbcCarRepository.getInstance().readCar(customer.getRentedCarId());
        var company = JdbcCompanyRepository.getInstance().readCompany(car.getCompanyId());
        System.out.println("Your rented car:");
        System.out.println(car.getName());
        System.out.println("Company:");
        System.out.println(company.getName());
        System.out.println();
        return new ConsoleCustomerMenu(customer);
    }
}
