package carsharing.persistance.repository;

import carsharing.businesslayer.Car;

import java.util.List;

public interface CarRepository {

    void createCar(Car car);

    Car readCar(int carId);

    List<Car> readCars(int companyId);
}
