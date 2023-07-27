package org.firstPF.services;

import org.firstPF.entities.Employee;
import org.firstPF.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(Employee employee) {
        if (employee.getProvider() == null || employee.getProvider().getId() == null) {
            throw new IllegalArgumentException("ProviderId is required.");
        }

        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            return null;
        }

        Employee employee = optionalEmployee.get();

        if (updatedEmployee.getProvider() == null || updatedEmployee.getProvider().getId() == null) {
            return null;
        }

        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());
        employee.setDateOfBirth(updatedEmployee.getDateOfBirth());
        employee.setContactNumber(updatedEmployee.getContactNumber());
        employee.setProvider(updatedEmployee.getProvider());

        return employeeRepository.save(employee);
    }
}
