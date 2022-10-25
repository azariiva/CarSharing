package carsharing.persistance.repository;

import carsharing.businesslayer.Company;
import carsharing.persistance.JdbcConnectionContainer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcCompanyRepository implements CompanyRepository {

    private static JdbcCompanyRepository jdbcCompanyRepository = null;

    public static JdbcCompanyRepository getInstance() {
        if (jdbcCompanyRepository == null) {
            jdbcCompanyRepository = new JdbcCompanyRepository();
        }
        return jdbcCompanyRepository;
    }

    private JdbcCompanyRepository() {
        final var sql = "CREATE TABLE IF NOT EXISTS COMPANY(" +
                "ID INT AUTO_INCREMENT PRIMARY KEY, " +
                "NAME VARCHAR(255) UNIQUE NOT NULL" +
                ")";

        try (var stmt = JdbcConnectionContainer.getConnection().createStatement()) {
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createCompany(Company company) {
        final var sql = "INSERT INTO COMPANY VALUES (DEFAULT, ?)";

        try (var stmt = JdbcConnectionContainer.getConnection().prepareStatement(sql)) {
            stmt.setString(1, company.getName());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Company readCompany(int companyId) {
        final var sql = "SELECT * FROM COMPANY WHERE ID=?";

        try (var stmt = JdbcConnectionContainer.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, companyId);
            var rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return convertToCompany(rs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Company> readCompanies() {
        final var sql = "SELECT * FROM COMPANY ORDER BY ID";

        try (var stmt = JdbcConnectionContainer.getConnection().createStatement()) {
            var rs = stmt.executeQuery(sql);
            var companies = new ArrayList<Company>();
            while (rs.next()) {
                var company = convertToCompany(rs);
                companies.add(company);
            }
            return companies;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Company convertToCompany(ResultSet rs) throws SQLException {
        var company = new Company();
        company.setId(rs.getInt("ID"));
        company.setName(rs.getString("NAME"));
        return company;
    }
}
