package org.firstPF.services;

import org.firstPF.ApplicationTests;
import org.firstPF.entities.Employee;
import org.firstPF.entities.Provider;
import org.firstPF.repositories.EmployeeRepository;
import org.firstPF.repositories.ProviderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@SpringBootTest
@DirtiesContext
public class EmployeeServiceTest extends ApplicationTests {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProviderRepository providerRepository;

    private Provider provider;

    @BeforeEach
    public void setUp() {
        employeeRepository.deleteAll();
        providerRepository.deleteAll();

        provider = new Provider();
        provider.setName("ABC Electronics");
        provider.setAddress("London 583/5");
        provider.setEmail("ABCElectronics@example.com");
        provider.setContactNumber("1234567890");
        provider = providerRepository.save(provider);
    }

    @Test
    public void testCreateAndGetEmployeeById() {
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");
        employee.setDateOfBirth(LocalDate.of(1990, 5, 15));
        employee.setContactNumber("9876543210");
        employee.setProvider(provider);

        Employee createdEmployee = employeeService.createEmployee(employee);
        Long createdEmployeeId = createdEmployee.getId();

        Optional<Employee> result = employeeService.getEmployeeById(createdEmployeeId);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(employee.getFirstName(), result.get().getFirstName());
        Assertions.assertEquals(employee.getLastName(), result.get().getLastName());
        Assertions.assertEquals(employee.getEmail(), result.get().getEmail());
        Assertions.assertEquals(employee.getDateOfBirth(), result.get().getDateOfBirth());
        Assertions.assertEquals(employee.getContactNumber(), result.get().getContactNumber());

    }

    @Test
    public void testGetAllEmployees() {
        Employee employee1 = new Employee();
        employee1.setFirstName("John");
        employee1.setLastName("Doe");
        employee1.setEmail("john.doe@example.com");
        employee1.setDateOfBirth(LocalDate.of(1990, 5, 15));
        employee1.setContactNumber("9876543210");
        employee1.setProvider(provider);
        employeeService.createEmployee(employee1);

        Employee employee2 = new Employee();
        employee2.setFirstName("Jane");
        employee2.setLastName("Smith");
        employee2.setEmail("jane.smith@example.com");
        employee2.setDateOfBirth(LocalDate.of(1985, 8, 25));
        employee2.setContactNumber("1234567890");
        employee2.setProvider(provider);
        employeeService.createEmployee(employee2);

        List<Employee> employees = employeeService.getAllEmployees();

        Assertions.assertEquals(2, employees.size());
        Assertions.assertEquals(employee1.getFirstName(), employees.get(0).getFirstName());
        Assertions.assertEquals(employee2.getFirstName(), employees.get(1).getFirstName());

    }

    @Test
    public void testDeleteEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");
        employee.setDateOfBirth(LocalDate.of(1990, 5, 15));
        employee.setContactNumber("9876543210");
        employee.setProvider(provider);
        employeeService.createEmployee(employee);

        Long employeeId = employee.getId();
        employeeService.deleteEmployee(employeeId);

        Optional<Employee> deletedEmployee = employeeService.getEmployeeById(employeeId);
        Assertions.assertFalse(deletedEmployee.isPresent());
    }
}
