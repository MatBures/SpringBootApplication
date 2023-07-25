package org.firstPF.controllers;

import org.firstPF.entities.Employee;
import org.firstPF.entities.Provider;
import org.firstPF.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEmployeeWithInvalidProviderId() {
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");
        employee.setDateOfBirth(LocalDate.of(1990, 5, 15));
        employee.setContactNumber("1234567890");

        ResponseEntity<?> response = employeeController.createEmployee(employee);

        verify(employeeService, never()).createEmployee(any(Employee.class));
        assert response.getStatusCode() == HttpStatus.BAD_REQUEST;
    }

    @Test
    void testCreateEmployeeWithValidProviderId() {
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");
        employee.setDateOfBirth(LocalDate.of(1990, 5, 15));
        employee.setContactNumber("1234567890");

        Provider provider = new Provider();
        provider.setId(1L);
        employee.setProvider(provider);

        when(employeeService.createEmployee(any(Employee.class))).thenReturn(employee);

        ResponseEntity<?> response = employeeController.createEmployee(employee);

        verify(employeeService, times(1)).createEmployee(any(Employee.class));
        assert response.getStatusCode() == HttpStatus.CREATED;
        assert response.getBody() instanceof Employee;
        Employee createdEmployee = (Employee) response.getBody();
        assert createdEmployee != null;
        assert createdEmployee.getFirstName().equals(employee.getFirstName());

    }

    @Test
    void testGetAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        Employee employee1 = new Employee();
        employee1.setFirstName("John");
        employee1.setLastName("Doe");
        employee1.setEmail("john.doe@example.com");
        employee1.setDateOfBirth(LocalDate.of(1990, 5, 15));
        employee1.setContactNumber("1234567890");
        employees.add(employee1);

        Employee employee2 = new Employee();
        employee2.setFirstName("Jane");
        employee2.setLastName("Smith");
        employee2.setEmail("jane.smith@example.com");
        employee2.setDateOfBirth(LocalDate.of(1995, 7, 25));
        employee2.setContactNumber("9876543210");
        employees.add(employee2);

        when(employeeService.getAllEmployees()).thenReturn(employees);

        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();

        verify(employeeService, times(1)).getAllEmployees();
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
        assert response.getBody().size() == employees.size();

    }

    @Test
    void testGetEmployeeById() {
        Long employeeId = 1L;
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");
        employee.setDateOfBirth(LocalDate.of(1990, 5, 15));
        employee.setContactNumber("1234567890");

        when(employeeService.getEmployeeById(employeeId)).thenReturn(Optional.of(employee));

        ResponseEntity<Employee> response = employeeController.getEmployeeById(employeeId);

        verify(employeeService, times(1)).getEmployeeById(employeeId);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
        assert response.getBody().getFirstName().equals(employee.getFirstName());
    }

    @Test
    void testGetEmployeeByIdNotFound() {
        Long employeeId = 999L;
        when(employeeService.getEmployeeById(employeeId)).thenReturn(Optional.empty());

        ResponseEntity<Employee> response = employeeController.getEmployeeById(employeeId);

        verify(employeeService, times(1)).getEmployeeById(employeeId);
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
    }

    @Test
    void testUpdateEmployeeWithInvalidProviderId() {
        Long employeeId = 1L;
        Employee existingEmployee = new Employee();
        existingEmployee.setFirstName("John");
        existingEmployee.setLastName("Doe");
        existingEmployee.setEmail("john.doe@example.com");
        existingEmployee.setDateOfBirth(LocalDate.of(1990, 5, 15));
        existingEmployee.setContactNumber("1234567890");

        Employee updatedEmployee = new Employee();
        updatedEmployee.setFirstName("Jane");
        updatedEmployee.setLastName("Smith");
        updatedEmployee.setEmail("jane.smith@example.com");
        updatedEmployee.setDateOfBirth(LocalDate.of(1995, 7, 25));
        updatedEmployee.setContactNumber("9876543210");

        when(employeeService.getEmployeeById(employeeId)).thenReturn(Optional.of(existingEmployee));

        ResponseEntity<?> response = employeeController.updateEmployee(employeeId, updatedEmployee);

        verify(employeeService, never()).createEmployee(any(Employee.class));
        assert response.getStatusCode() == HttpStatus.BAD_REQUEST;
    }

    @Test
    void testUpdateEmployeeWithValidProviderId() {
        Long employeeId = 1L;
        Employee existingEmployee = new Employee();
        existingEmployee.setFirstName("John");
        existingEmployee.setLastName("Doe");
        existingEmployee.setEmail("john.doe@example.com");
        existingEmployee.setDateOfBirth(LocalDate.of(1990, 5, 15));
        existingEmployee.setContactNumber("1234567890");

        Employee updatedEmployee = new Employee();
        updatedEmployee.setFirstName("Jane");
        updatedEmployee.setLastName("Smith");
        updatedEmployee.setEmail("jane.smith@example.com");
        updatedEmployee.setDateOfBirth(LocalDate.of(1995, 7, 25));
        updatedEmployee.setContactNumber("9876543210");

        Provider provider = new Provider();
        provider.setId(1L);
        updatedEmployee.setProvider(provider);

        when(employeeService.getEmployeeById(employeeId)).thenReturn(Optional.of(existingEmployee));
        when(employeeService.createEmployee(any(Employee.class))).thenReturn(updatedEmployee);

        ResponseEntity<?> response = employeeController.updateEmployee(employeeId, updatedEmployee);

        verify(employeeService, times(1)).getEmployeeById(employeeId);
        verify(employeeService, times(1)).createEmployee(any(Employee.class));
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() instanceof Employee;
        Employee updatedEmployeeEntity = (Employee) response.getBody();
        assert updatedEmployeeEntity != null;
        assert updatedEmployeeEntity.getFirstName().equals(updatedEmployee.getFirstName());
    }

    @Test
    void testUpdateEmployeeNotFound() {
        Long employeeId = 999L;
        Employee updatedEmployee = new Employee();
        updatedEmployee.setFirstName("Jane");
        updatedEmployee.setLastName("Smith");
        updatedEmployee.setEmail("jane.smith@example.com");
        updatedEmployee.setDateOfBirth(LocalDate.of(1995, 7, 25));
        updatedEmployee.setContactNumber("9876543210");

        Provider provider = new Provider();
        provider.setId(1L);
        updatedEmployee.setProvider(provider);

        when(employeeService.getEmployeeById(employeeId)).thenReturn(Optional.empty());

        ResponseEntity<?> response = employeeController.updateEmployee(employeeId, updatedEmployee);

        verify(employeeService, times(1)).getEmployeeById(employeeId);
        verify(employeeService, never()).createEmployee(any(Employee.class));
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
    }

    @Test
    void testDeleteEmployee() {
        Long employeeId = 1L;

        when(employeeService.getEmployeeById(employeeId)).thenReturn(Optional.of(new Employee()));

        ResponseEntity<Void> response = employeeController.deleteEmployee(employeeId);

        verify(employeeService, times(1)).getEmployeeById(employeeId);
        verify(employeeService, times(1)).deleteEmployee(employeeId);
        assert response.getStatusCode() == HttpStatus.NO_CONTENT;
    }

    @Test
    void testDeleteEmployeeNotFound() {
        Long employeeId = 999L;

        when(employeeService.getEmployeeById(employeeId)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = employeeController.deleteEmployee(employeeId);

        verify(employeeService, times(1)).getEmployeeById(employeeId);
        verify(employeeService, never()).deleteEmployee(employeeId);
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
    }
}
