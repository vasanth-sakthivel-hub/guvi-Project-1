package com.example.EmployeesManagementApplication.controller;

import com.example.EmployeesManagementApplication.Controller.EmployeeController;
import com.example.EmployeesManagementApplication.EmployeesManagementApplication;
import com.example.EmployeesManagementApplication.Model.Employee;
import com.example.EmployeesManagementApplication.Repository.EmployeeRepository;
import com.example.EmployeesManagementApplication.Service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;



@WebMvcTest(EmployeeController.class)
@ContextConfiguration(classes = EmployeesManagementApplication.class)

public class EmployeeControllerTests{

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;
    @MockitoBean
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testViewHomePage() throws Exception {
        Employee emp1 = new Employee();
        emp1.setId(1L);
        emp1.setName("John");
        emp1.setEmail("john@example.com");

        Employee emp2 = new Employee();
        emp2.setId(2L);
        emp2.setName("Jane");
        emp2.setEmail("jane@example.com");

        given(employeeService.getAllEmployee()).willReturn(Arrays.asList(emp1, emp2));

        mockMvc.perform(MockMvcRequestBuilders.get("/home"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allemplist"))
                .andExpect(view().name("index"));
    }

    @Test
    public void testShowAddNewEmployee() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/add"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("employee"))
                .andExpect(view().name("addemployee"));
    }

    @Test
    public void testAddEmployeeSuccess() throws Exception {
        Employee employee = new Employee();
        employee.setId(0);
        employee.setEmail("test@example.com");

        when(employeeRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        mockMvc.perform(post("/save")
                        .param("id", "0")
                        .param("email", "test@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"))
                .andExpect(flash().attribute("success", "Employee added successfully!"));
    }

    @Test
    public void testUpdateFormReturnsEmployeeModel() throws Exception {
        long employeeId = 1L;

        Employee mockEmployee = new Employee();
        mockEmployee.setId(employeeId);
        mockEmployee.setName("vikiram");
        mockEmployee.setEmail("vikiram@example.com");

        when(employeeService.getById(employeeId)).thenReturn(mockEmployee);

        mockMvc.perform(get("/updateform/{id}", employeeId))
                .andExpect(status().isOk())
                .andExpect(view().name("update"))
                .andExpect(model().attributeExists("employee"))
                .andExpect(model().attribute("employee", mockEmployee));
    }


}
