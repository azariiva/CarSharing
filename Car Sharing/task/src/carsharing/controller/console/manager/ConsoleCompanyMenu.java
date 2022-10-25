package carsharing.controller.console.manager;

import carsharing.businesslayer.Car;
import carsharing.controller.AppState;
import carsharing.controller.console.ConsoleMenu;
import carsharing.controller.console.SysoutScannerContainer;
import carsharing.persistance.repository.JdbcCarRepository;

import java.util.Map;
import java.util.stream.IntStream;

public class ConsoleCompanyMenu extends ConsoleMenu {

    private static final String[] MENU_OPTIONS_LIST = {
            "1. Car list",
            "2. Create a car",
            "0. Back"
    };

    protected ConsoleCompanyMenu(int companyId) {
        super(MENU_OPTIONS_LIST, Map.of(
                0, ConsoleManagerMenu::new,
                1, () -> handleCarListOption(companyId),
                2, () -> handleCreateCarOption(companyId)
        ));
    }

    private static AppState handleCarListOption(int companyId) {
        var cars = JdbcCarRepository.getInstance().readCars(companyId);
        if (!cars.isEmpty()) {
            System.out.println("Car list:");
            IntStream.range(0, cars.size())
                            .forEach(idx -> System.out.printf("%d. %s\n", idx + 1, cars.get(idx).getName()));
        } else {
            System.out.println("The car list is empty!");
        }
        System.out.println();
        return new ConsoleCompanyMenu(companyId);
    }

    private static ConsoleCompanyMenu handleCreateCarOption(int companyId) {
        var car = new Car();
        car.setCompanyId(companyId);
        car.setName(askCarName());
        JdbcCarRepository.getInstance().createCar(car);
        System.out.println("The car was added!");
        System.out.println();
        return new ConsoleCompanyMenu(companyId);
    }

    private static String askCarName() {
        System.out.println("Enter the car name:");
        return SysoutScannerContainer.getScanner().nextLine().trim();
    }
}
