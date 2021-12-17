package com.example.Project.controllers;

import com.example.Project.domains.Department;
import com.example.Project.domains.Employee;
import com.example.Project.domains.EmployeeIDCard;
import com.example.Project.repos.DepartmentRepo;
import com.example.Project.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @GetMapping("/")
    public String helloPage() {
        return "helloPage";
    }


    @GetMapping("/info")
    public ResponseEntity<String> getAppDetails() {
        return new ResponseEntity<>("Name: " + appName + " , " + "version: " + appVersion, OK);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getPagesOfEmployees(@RequestParam Integer pageNumber,
                                                              @RequestParam Integer pageSize) {
        return new ResponseEntity<>(service.getPagesOfEmployees(pageNumber, pageSize), OK);
    }


    @GetMapping("/employees/{id}")
    public ResponseEntity<String> getEmployee(@PathVariable Long id) {
        Employee e = service.getOrDeleteEmployeeById(id);
        if (e == null) return new ResponseEntity<>(
                "No employee with id= " + id + " was fount in the database!", BAD_REQUEST
        );

        return new ResponseEntity<>(
                "The employee details for the id = " + id + " is " + e, OK
        );
    }

    @PostMapping("/employees")
    private ResponseEntity<Employee> saveEmployee(@Valid @RequestBody Employee employee,
                                                  @RequestBody EmployeeIDCard idCard) {
        return new ResponseEntity<>(service.saveEmployee(employee, idCard), CREATED);
    }

    @DeleteMapping("/employees")
    public ResponseEntity<String> deleteEmployee(@RequestParam("id") long id) {
        Employee e = service.getOrDeleteEmployeeById(id);
        if (e == null) return new ResponseEntity<>(
                "No employee with id= " + id + " was fount in the database!", BAD_REQUEST
        );

        return new ResponseEntity<>(
                "The employee with id = " + id + " was successfully deleted", NO_CONTENT
        );
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<String> editEmployee(
            @PathVariable Long id,
            @RequestBody Employee employee,
            @RequestBody EmployeeIDCard idCard){
        employee.setId(id);
        service.saveEmployee(employee, idCard);
        return new ResponseEntity<>("Employee with id: " + employee.getId() + " has been successfully edited", OK);
    }

    @GetMapping("employees/filter/nameAndLocation")
    public ResponseEntity<List<Employee>> getByNameAndLocation(@RequestParam String name,
                                                               @RequestParam String email) {
        return new ResponseEntity<>(service.getEmployeeByNameAndEmail(name, email), OK);
    }

    @GetMapping("employees/filter/nameKeyword")
    public ResponseEntity<List<Employee>> getByNameAndLocation(@RequestParam String keyword) {
        return new ResponseEntity<>(service.getEmployeeByKeyword(keyword), OK);
    }

    @GetMapping("employees/{name}/{location}")
    public ResponseEntity<List<Employee>> getByNameOrEmail(@PathVariable String name,
                                                              @PathVariable String email) {
        return new ResponseEntity<>(service.getEmployeeByNameOrEmail(name, email), OK);
    }

    @DeleteMapping("employees/delete/{name}")
    public ResponseEntity<String> deleteByName(@PathVariable String name) {
        return new ResponseEntity<>(service.deleteByName(name) + " amount of records was deleted", NO_CONTENT);
    }

}
