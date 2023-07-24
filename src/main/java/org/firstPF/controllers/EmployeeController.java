package org.firstPF.controllers;

import org.firstPF.entities.Employee;
import org.firstPF.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        // Check if providerId is provided
        if (employee.getProvider() == null || employee.getProvider().getId() == null) {
            return ResponseEntity.badRequest().body("ProviderId is required.");
        }

        Employee createdEmployee = employeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id).orElse(null);
        if (employee == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        Employee employee = employeeService.getEmployeeById(id).orElse(null);
        if (employee == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Check if providerId and customerId are provided in the updatedOffer
        if (updatedEmployee.getProvider() == null || updatedEmployee.getProvider().getId() == null) {
            return ResponseEntity.badRequest().body("ProviderId is required for updating an employee.");
        }

        // Update the employee properties with the updatedEmployee
        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());
        employee.setDateOfBirth(updatedEmployee.getDateOfBirth());
        employee.setContactNumber(updatedEmployee.getContactNumber());
        employee.setProvider(updatedEmployee.getProvider());

        Employee updatedEmployeeEntity = employeeService.createEmployee(employee);
        return new ResponseEntity<>(updatedEmployeeEntity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        if (employee.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

}
