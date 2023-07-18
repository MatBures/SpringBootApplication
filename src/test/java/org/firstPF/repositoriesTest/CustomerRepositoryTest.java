package org.firstPF.repositoriesTest;


import org.firstPF.ApplicationTests;
import org.firstPF.entities.Customer;
import org.firstPF.repositories.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

@DirtiesContext
public class CustomerRepositoryTest extends ApplicationTests {

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    public void testFindById() {
        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setContactNumber("1234567890");
        customerRepository.save(customer);

        Optional<Customer> result = customerRepository.findById(customer.getId());

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(customer.getName(), result.get().getName());
        Assertions.assertEquals(customer.getEmail(), result.get().getEmail());
        Assertions.assertEquals(customer.getContactNumber(), result.get().getContactNumber());
    }

    @Test
    public void testSave() {
        Customer customer = new Customer();
        customer.setName("Jane Smith");
        customer.setEmail("jane.smith@example.com");
        customer.setContactNumber("9876543210");

        Customer savedCustomer = customerRepository.save(customer);

        Assertions.assertNotNull(savedCustomer.getId());

        Customer retrievedCustomer = customerRepository.findById(savedCustomer.getId()).get();
        Assertions.assertEquals(customer.getName(), retrievedCustomer.getName());
        Assertions.assertEquals(customer.getEmail(), retrievedCustomer.getEmail());
        Assertions.assertEquals(customer.getContactNumber(), retrievedCustomer.getContactNumber());
    }
}
