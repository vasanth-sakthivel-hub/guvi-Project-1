package com.example.EmployeesManagementApplication.service;

import com.example.EmployeesManagementApplication.Model.Employee;
import com.example.EmployeesManagementApplication.Repository.EmployeeRepository;
import com.example.EmployeesManagementApplication.Service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    public void setup(){
        employeeRepository.deleteAll();
    }

    @Test
    public void givenEmployee_whenSave_thenReturnSavedEmployee() {
        // given
        Employee employee = new Employee();
        employee.setName("vikiram");
        employee.setEmail("vikiram@gmail.com");
        employee.setDesignation("web developer");

        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());

        given(employeeRepository.save(employee))
                .willReturn(employee);

        // when
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // then
        assertThat(savedEmployee).isNotNull();
    }

    @Test
    public void givenDuplicateEmployeeName_whenSave_thenThrowException() {
        // given
        Employee employee = new Employee();
        employee.setName("vikiram");
        employee.setEmail("vikiram@gmail.com");
        employee.setDesignation("web developer");

        given(employeeRepository.existsByName(employee.getName()))
                .willReturn(true);

        // when & then
        assertThatThrownBy(() -> employeeService.saveEmployee(employee))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Name already used");
    }

    @Test
    public void givenValidId_whenGetById_thenReturnEmployee() {
        // given
        Long id = 1L;
        Employee employee = new Employee();
        employee.setId(id);
        employee.setName("Vikiram");
        employee.setEmail("vikiram@gmail.com");
        employee.setDesignation("Web Developer");

        given(employeeRepository.findById(id)).willReturn(Optional.of(employee));

        // when
        Employee foundEmployee = employeeService.getById(id);

        // then
        assertThat(foundEmployee).isNotNull();
        assertThat(foundEmployee.getId()).isEqualTo(id);
    }

    @Test
    void testDeleteById_whenIdIsNotNull_callsRepositoryDelete() {
        // given
        Long id = 1L;

        // when
        employeeService.deleteById(id);

        // then
        verify(employeeRepository, times(1)).deleteById(id);
    }


    @Test
    void testDeleteById_whenIdIsNull_doesNotCallRepositoryDelete() {
        // given
        Long id = null;

        // when
        employeeService.deleteById(id);

        // then
        verify(employeeRepository, never()).deleteById(any());
    }



}
