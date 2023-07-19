package org.firstPF.repositories;

import org.firstPF.ApplicationTests;
import org.firstPF.entities.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;


import java.time.LocalDate;

import java.util.Optional;

@SpringBootTest
@DirtiesContext
public class EmployeeRepositoryTest extends ApplicationTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setUp() {
        employeeRepository.deleteAll();
    }

    @Test
    public void testSaveAndFindById() {
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
}