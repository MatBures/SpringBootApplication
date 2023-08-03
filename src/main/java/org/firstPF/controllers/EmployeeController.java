package org.firstPF.controllers;

import jakarta.persistence.EntityNotFoundException;
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
        Employee createdEmployee = employeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id).orElse(null);
        if (employee == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
            Employee updatedEmployeeEntity = employeeService.updateEmployee(id, updatedEmployee);
            if (updatedEmployeeEntity == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(updatedEmployeeEntity);
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
