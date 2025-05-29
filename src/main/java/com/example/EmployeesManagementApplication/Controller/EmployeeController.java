
package com.example.EmployeesManagementApplication.Controller;


import com.example.EmployeesManagementApplication.Model.Employee;
import com.example.EmployeesManagementApplication.Repository.EmployeeRepository;
import com.example.EmployeesManagementApplication.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;
import java.util.Optional;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;


    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/home")
    public String viewHomePage(Model model){
        model.addAttribute("allemplist", employeeService.getAllEmployee());
        return "index";
    }

    @GetMapping("/add")
    public String showAddNewEmployee(Model model){
        Employee employee = new Employee();
        model.addAttribute("employee", new Employee());
        return "addemployee";
    }



    @PostMapping("/save")
    public String save(@ModelAttribute("employee") Employee employee,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        Optional<Employee> existingEmployee = employeeRepository.findByEmail(employee.getEmail());

        if (existingEmployee.isPresent() && !Objects.equals(existingEmployee.get().getId(), employee.getId())) {
            model.addAttribute("employee", employee);
            model.addAttribute("error", "Email already exists!");
            return (employee.getId() == 0 ) ? "addemployee" : "updateemployee";
        }

        employeeRepository.save(employee);

        redirectAttributes.addFlashAttribute("success",
                (employee.getId() == 0 ? "Employee added" : "Employee updated") + " successfully!");

        return "redirect:/home";
    }






    @GetMapping("updateform/{id}")
    public String updateForm(@PathVariable(value = "id") long id, Model model){
        Employee employee = employeeService.getById(id);
        model.addAttribute("employee", employee);
        return "update";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable(value = "id") long id){
        employeeService.deleteById(id);
        return "redirect:/home";
    }


}
