package com.example.EmployeesManagementApplication.repository;

import com.example.EmployeesManagementApplication.Model.Employee;
import com.example.EmployeesManagementApplication.Repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureDataJpa
@SpringBootTest
public class EmployeeRepositorytests {


    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setup(){
        employeeRepository.deleteAll();
    }


    @Test
    public void givenEmployee_whenSave_thenReturnSaveEmployee(){
        //given
        Employee employee = new Employee();
        employee.setName("vikiram");
        employee.setEmail("vikiram@gamil");
        employee.setDesignation("web developer");

        //when
        Employee savedEmployee = employeeRepository.save(employee);
        //then
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @Test
    public void givenEmployeeList_whenFindAll_thenReturnEmployeeList(){
        //given
        Employee employee = new Employee();
        employee.setName("viki");
        employee.setEmail("viki@gamil");
        employee.setDesignation("web developer");
        Employee employee1 = new Employee();
        employee1.setName("ram");
        employee1.setEmail("ram@gamil");
        employee1.setDesignation("web developer");


        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        //when
        List<Employee> employeeList = employeeRepository.findAll();
        //then
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployee(){
        //given
        Employee employee = new Employee();
        employee.setName("viki");
        employee.setEmail("viki@gamil");
        employee.setDesignation("web developer");


        employeeRepository.save(employee);

        //when
        Employee employee1 = employeeRepository.findById(employee.getId()).get();
        //then
        assertThat(employee1).isNotNull();

    }













}


