package carsharing.persistance.repository;

import carsharing.businesslayer.Car;
import carsharing.persistance.JdbcConnectionContainer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcCarRepository implements CarRepository {

    private static JdbcCarRepository jdbcCarRepository = null;

    public static JdbcCarRepository getInstance() {
        if (jdbcCarRepository == null) {
            jdbcCarRepository = new JdbcCarRepository();
        }
        return jdbcCarRepository;
    }

    private JdbcCarRepository() {
        final var sql = "CREATE TABLE IF NOT EXISTS CAR(" +
                "ID INT AUTO_INCREMENT PRIMARY KEY, " +
                "NAME VARCHAR(255) UNIQUE NOT NULL, " +
                "COMPANY_ID INT NOT NULL, " +
                "FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID)" +
                ")";

        try (var stmt = JdbcConnectionContainer.getConnection().createStatement()) {
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createCar(Car car) {
        final var sql = "INSERT INTO CAR VALUES (DEFAULT, ?, ?)";

        try (var stmt = JdbcConnectionContainer.getConnection().prepareStatement(sql)) {
            stmt.setString(1, car.getName());
            stmt.setInt(2, car.getCompanyId());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Car readCar(int carId) {
        final var sql = "SELECT * FROM CAR WHERE ID=?";

        try (var stmt = JdbcConnectionContainer.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, carId);
            var rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return convertToCar(rs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Car> readCars(int companyId) {
        final var sql = "SELECT CAR.ID AS ID, CAR.NAME AS NAME, CAR.COMPANY_ID AS COMPANY_ID " +
                "FROM CAR LEFT JOIN CUSTOMER ON CAR.ID = CUSTOMER.RENTED_CAR_ID " +
                "WHERE CUSTOMER.NAME IS NULL AND COMPANY_ID = ? ORDER BY ID;";

        try (var stmt = JdbcConnectionContainer.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, companyId);
            var rs = stmt.executeQuery();
            var cars = new ArrayList<Car>();
            while (rs.next()) {
                var car = convertToCar(rs);
                cars.add(car);
            }
            return cars;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Car convertToCar(ResultSet rs) throws SQLException {
        var car = new Car();
        car.setId(rs.getInt("ID"));
        car.setName(rs.getString("NAME"));
        car.setCompanyId(rs.getInt("COMPANY_ID"));
        return car;
    }
}
