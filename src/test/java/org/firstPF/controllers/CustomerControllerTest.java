package org.firstPF.controllers;

import org.firstPF.entities.Customer;
import org.firstPF.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@DirtiesContext
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomer() {
        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setContactNumber("1234567890");

        when(customerService.createCustomer(any(Customer.class))).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.createCustomer(customer);

        verify(customerService, times(1)).createCustomer(any(Customer.class));
        assert response.getStatusCode() == HttpStatus.CREATED;
        assert response.getBody() != null;
        assert response.getBody().getName().equals(customer.getName());

    }

    @Test
    void testGetAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        Customer customer1 = new Customer();
        customer1.setName("John Doe");
        customer1.setEmail("john.doe@example.com");
        customer1.setContactNumber("1234567890");
        customers.add(customer1);

        Customer customer2 = new Customer();
        customer2.setName("Jane Smith");
        customer2.setEmail("jane.smith@example.com");
        customer2.setContactNumber("9876543210");
        customers.add(customer2);

        when(customerService.getAllCustomers()).thenReturn(customers);

        ResponseEntity<List<Customer>> response = customerController.getAllCustomers();

        verify(customerService, times(1)).getAllCustomers();
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
        assert response.getBody().size() == customers.size();

    }

    @Test
    void testGetCustomerById() {
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setContactNumber("1234567890");

        when(customerService.getCustomerById(customerId)).thenReturn(Optional.of(customer));

        ResponseEntity<Customer> response = customerController.getCustomerById(customerId);

        verify(customerService, times(1)).getCustomerById(customerId);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
        assert response.getBody().getName().equals(customer.getName());

    }

    @Test
    void testGetCustomerByIdNotFound() {
        Long customerId = 999L;
        when(customerService.getCustomerById(customerId)).thenReturn(Optional.empty());

        ResponseEntity<Customer> response = customerController.getCustomerById(customerId);

        verify(customerService, times(1)).getCustomerById(customerId);
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
    }

    @Test
    void testUpdateCustomerSuccess() {
        Long customerId = 1L;
        Customer existingCustomer = new Customer();
        existingCustomer.setName("John");
        existingCustomer.setEmail("john@example.com");
        existingCustomer.setContactNumber("1234567890");

        Customer updatedCustomer = new Customer();
        updatedCustomer.setName("John Doe");
        updatedCustomer.setEmail("johndoe@example.com");
        updatedCustomer.setContactNumber("5555555555");

        when(customerService.getCustomerById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(customerService.updateCustomer(customerId, updatedCustomer)).thenReturn(updatedCustomer);

        ResponseEntity<Customer> response = customerController.updateCustomer(customerId, updatedCustomer);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedCustomer.getName(), response.getBody().getName());
        assertEquals(updatedCustomer.getEmail(), response.getBody().getEmail());
        assertEquals(updatedCustomer.getContactNumber(), response.getBody().getContactNumber());
    }


    @Test
    void testUpdateCustomerNotFound() {
        Long customerId = 95L;
        Customer updatedCustomer = new Customer();
        updatedCustomer.setName("John Doe");
        updatedCustomer.setEmail("johndoe@example.com");
        updatedCustomer.setContactNumber("5555555555");

        when(customerService.getCustomerById(customerId)).thenReturn(Optional.empty());

        ResponseEntity<Customer> response = customerController.updateCustomer(customerId, updatedCustomer);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteCustomer() {
        Long customerId = 1L;

        when(customerService.getCustomerById(customerId)).thenReturn(Optional.of(new Customer()));

        ResponseEntity<Void> response = customerController.deleteCustomer(customerId);

        verify(customerService, times(1)).getCustomerById(customerId);
        verify(customerService, times(1)).deleteCustomer(customerId);
        assert response.getStatusCode() == HttpStatus.NO_CONTENT;
    }

    @Test
    void testDeleteCustomerNotFound() {
        Long customerId = 999L;

        when(customerService.getCustomerById(customerId)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = customerController.deleteCustomer(customerId);

        verify(customerService, times(1)).getCustomerById(customerId);
        verify(customerService, never()).deleteCustomer(customerId);
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
    }
}
