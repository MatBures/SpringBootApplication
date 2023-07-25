package org.firstPF.services;

import org.firstPF.ApplicationTests;
import org.firstPF.entities.Customer;
import org.firstPF.repositories.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@DirtiesContext
public class CustomerServiceTest extends ApplicationTests {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    public void testCreateAndGetCustomerById() {
        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setContactNumber("1234567890");

        Customer createdCustomer = customerService.createCustomer(customer);
        Long createdCustomerId = createdCustomer.getId();

        Optional<Customer> result = customerService.getCustomerById(createdCustomerId);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(customer.getName(), result.get().getName());
        Assertions.assertEquals(customer.getEmail(), result.get().getEmail());
        Assertions.assertEquals(customer.getContactNumber(), result.get().getContactNumber());
    }

    @Test
    public void testGetAllCustomers() {
        Customer customer1 = new Customer();
        customer1.setName("John Doe");
        customer1.setEmail("john.doe@example.com");
        customer1.setContactNumber("1234567890");
        customerService.createCustomer(customer1);

        Customer customer2 = new Customer();
        customer2.setName("Jane Smith");
        customer2.setEmail("jane.smith@example.com");
        customer2.setContactNumber("9876543210");
        customerService.createCustomer(customer2);

        List<Customer> customers = customerService.getAllCustomers();

        Assertions.assertEquals(2, customers.size());
        Assertions.assertEquals(customer1.getName(), customers.get(0).getName());
        Assertions.assertEquals(customer2.getName(), customers.get(1).getName());
    }

    @Test
    public void testDeleteCustomer() {
        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setContactNumber("1234567890");
        customerService.createCustomer(customer);

        Long customerId = customer.getId();
        customerService.deleteCustomer(customerId);

        Optional<Customer> deletedCustomer = customerService.getCustomerById(customerId);
        Assertions.assertFalse(deletedCustomer.isPresent());
    }
}

