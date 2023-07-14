package org.firstPF.repositoriesTest;

import org.firstPF.entities.Employee;
import org.firstPF.repositories.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Date;
import java.util.Optional;

@DataJpaTest
@SpringJUnitConfig
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFindById() {
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");
        employee.setDateOfBirth(new Date());
        employee.setContactNumber("1234567890");
        entityManager.persistAndFlush(employee);

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
        employee.setDateOfBirth(new Date());
        employee.setContactNumber("9876543210");

        Employee savedEmployee = employeeRepository.save(employee);

        Assertions.assertNotNull(savedEmployee.getId());

        Employee retrievedEmployee = entityManager.find(Employee.class, savedEmployee.getId());
        Assertions.assertEquals(employee.getFirstName(), retrievedEmployee.getFirstName());
        Assertions.assertEquals(employee.getLastName(), retrievedEmployee.getLastName());
        Assertions.assertEquals(employee.getEmail(), retrievedEmployee.getEmail());
        Assertions.assertEquals(employee.getDateOfBirth(), retrievedEmployee.getDateOfBirth());
        Assertions.assertEquals(employee.getContactNumber(), retrievedEmployee.getContactNumber());
    }
}
