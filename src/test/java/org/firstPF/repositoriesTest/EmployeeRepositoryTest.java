package org.firstPF.repositoriesTest;

import org.firstPF.ApplicationTests;
import org.firstPF.entities.Employee;
import org.firstPF.repositories.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.annotation.DirtiesContext;


import java.time.LocalDate;

import java.util.Optional;

@DirtiesContext
public class EmployeeRepositoryTest extends ApplicationTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setUp() {
        employeeRepository.deleteAll();
    }

    @Test
    public void testFindById() {
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");
        employee.setDateOfBirth(LocalDate.of(1990, 1, 1));
        employee.setContactNumber("1234567890");
        employeeRepository.save(employee);

        Optional<Employee> result = employeeRepository.findById(employee.getId());

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(employee.getFirstName(), result.get().getFirstName());
        Assertions.assertEquals(employee.getLastName(), result.get().getLastName());
        Assertions.assertEquals(employee.getEmail(), result.get().getEmail());
        Assertions.assertEquals(employee.getDateOfBirth(), result.get().getDateOfBirth());
        Assertions.assertEquals(employee.getContactNumber(), result.get().getContactNumber());
    }

    @Test
    public void testSave() {
        Employee employee = new Employee();
        employee.setFirstName("Jane");
        employee.setLastName("Smith");
        employee.setEmail("jane.smith@example.com");
        employee.setDateOfBirth(LocalDate.of(1990, 1, 1));
        employee.setContactNumber("9876543210");

        Employee savedEmployee = employeeRepository.save(employee);

        Assertions.assertNotNull(savedEmployee.getId());

        Employee retrievedEmployee = employeeRepository.findById(savedEmployee.getId()).get();
        Assertions.assertEquals(employee.getFirstName(), retrievedEmployee.getFirstName());
        Assertions.assertEquals(employee.getLastName(), retrievedEmployee.getLastName());
        Assertions.assertEquals(employee.getEmail(), retrievedEmployee.getEmail());
        Assertions.assertEquals(employee.getDateOfBirth(), retrievedEmployee.getDateOfBirth());
        Assertions.assertEquals(employee.getContactNumber(), retrievedEmployee.getContactNumber());
    }
}