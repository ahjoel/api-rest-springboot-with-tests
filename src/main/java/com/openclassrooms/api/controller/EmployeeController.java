package com.openclassrooms.api.controller;

import com.openclassrooms.api.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.openclassrooms.api.service.EmployeeService;

import java.util.Optional;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public Iterable<Employee> getEmployees(){
        return employeeService.getEmployees();
    }

    @PostMapping("/employee")
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeService.saveEmployee(employee);
    }

    @GetMapping("/employee/{id}")
    public Employee getEmployee(@PathVariable("id") final Long id) {
        Optional<Employee> employee = employeeService.getEmployee(id);
        if (employee.isPresent()){
            return employee.get();
        }else
            return null;
    }

    @PutMapping("/employee/{id}")
    public Employee updateEmployee(@PathVariable("id") final Long id){
        Optional<Employee> e = employeeService.getEmployee(id);

        if (e.isPresent()){
            Employee currentEmployee = e.get();

            String firstName = currentEmployee.getFirstName();
            if (firstName != null){
                currentEmployee.setFirstName(firstName);
            }

            String lastName = currentEmployee.getLastName();
            if (lastName != null){
                currentEmployee.setLastName(lastName);
            }

            String mail = currentEmployee.getMail();
            if (mail != null){
                currentEmployee.setMail(mail);
            }

            String password = currentEmployee.getPassword();
            if (password != null){
                currentEmployee.setPassword(password);
            }
            employeeService.saveEmployee(currentEmployee);
            return currentEmployee;
        }else {
            return null;
        }
    }

    @DeleteMapping("/employee/{id}")
    public void deleteEmployee(@PathVariable("id") final Long id){employeeService.deleteEmployee(id);}


}
