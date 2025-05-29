package com.example.EmployeesManagementApplication.Service;

import com.example.EmployeesManagementApplication.Model.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployee();

    Employee saveEmployee(Employee employee); // ✅ Fixed

    Employee getById(Long id);

    void deleteById(Long id);
}
