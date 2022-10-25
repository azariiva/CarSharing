package carsharing.persistance.repository;

import carsharing.businesslayer.Customer;

import java.util.List;

public interface CustomerRepository {

    void createCustomer(Customer customer);

    void updateCustomer(Customer customer);

    Customer readCustomer(int customerId);

    List<Customer> readCustomers();
}
