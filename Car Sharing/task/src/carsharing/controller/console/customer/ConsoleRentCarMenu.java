package carsharing.controller.console.customer;

import carsharing.businesslayer.Company;
import carsharing.businesslayer.Customer;
import carsharing.controller.AppState;
import carsharing.controller.console.ConsoleMenu;
import carsharing.controller.console.ConsoleUtils;
import carsharing.persistance.repository.JdbcCarRepository;
import carsharing.persistance.repository.JdbcCompanyRepository;
import carsharing.persistance.repository.JdbcCustomerRepository;

import java.util.AbstractMap;
import java.util.Map;

public class ConsoleRentCarMenu {

    public static class ConsoleRentCarMenuCompanySelection extends ConsoleMenu {

        public static AppState getConsoleRentCarMenuCompanySelection(Customer customer) {
            final var companies = JdbcCompanyRepository.getInstance().readCompanies();

            if (companies.isEmpty()) {
                return () -> {
                    System.out.println("The company list is empty!");
                    System.out.println();
                    return new ConsoleCustomerMenu(customer);
                };
            }

            final var menuOptionsList = ConsoleUtils.generateMenuOptionsList(
                    companies,
                    idx -> companies.get(idx).getName(),
                    "0. Back"
            );
            final var menuOptionTransitions = ConsoleUtils.generateMenuOptionTransitions(
                    companies,
                    idx -> () -> ConsoleRentCarMenuCarSelection.getConsoleRentCarMenuCarSelection(customer, companies.get(idx)),
                    new AbstractMap.SimpleImmutableEntry<>(0, new ConsoleCustomerMenu(customer))
            );
            return new ConsoleRentCarMenuCompanySelection(menuOptionsList, menuOptionTransitions);

        }

        private ConsoleRentCarMenuCompanySelection(String[] menuOptionsList, Map<Integer, AppState> menuOptionTransitions) {
            super(menuOptionsList, menuOptionTransitions);
        }

        @Override
        public void showMenu() {
            System.out.println("Choose a company:");
            super.showMenu();
        }
    }

    public static class ConsoleRentCarMenuCarSelection extends ConsoleMenu {

        public static AppState getConsoleRentCarMenuCarSelection(Customer customer, Company company) {
            final var cars = JdbcCarRepository.getInstance().readCars(company.getId());

            if (cars.isEmpty()) {
                System.out.printf("No available cars in the '%s' company\n", company.getName());
                System.out.println();
                return ConsoleRentCarMenuCompanySelection.getConsoleRentCarMenuCompanySelection(customer);
            }

            final var menuOptionsList = ConsoleUtils.generateMenuOptionsList(
                    cars,
                    idx -> cars.get(idx).getName(),
                    "0. Back"
            );
            final var menuOptionTransitions = ConsoleUtils.generateMenuOptionTransitions(
                    cars,
                    idx -> () -> {
                        var car = cars.get(idx);
                        customer.setRentedCarId(car.getId());
                        JdbcCustomerRepository.getInstance().updateCustomer(customer);
                        System.out.printf("You rented '%s'\n", car.getName());
                        System.out.println();
                        return new ConsoleCustomerMenu(customer);
                    },
                    new AbstractMap.SimpleImmutableEntry<>(0, ConsoleRentCarMenuCompanySelection.getConsoleRentCarMenuCompanySelection(customer))
            );
            return new ConsoleRentCarMenuCarSelection(menuOptionsList, menuOptionTransitions);
        }

        private ConsoleRentCarMenuCarSelection(String[] menuOptionsList, Map<Integer, AppState> menuOptionTransitions) {
            super(menuOptionsList, menuOptionTransitions);
        }

        @Override
        public void showMenu() {
            System.out.println("Choose a car:");
            super.showMenu();
        }
    }
}
