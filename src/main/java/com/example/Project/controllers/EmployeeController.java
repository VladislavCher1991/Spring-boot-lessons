package com.example.Project.controllers;

import com.example.Project.domains.Employee;
import com.example.Project.service.EmployeeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
public class EmployeeController {

    private final EmployeeService service;

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/info")
    public ResponseEntity<String> getAppDetails() {
        return new ResponseEntity<>("Name: " + appName + " , " + "version: " + appVersion, OK);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getEmployees(@RequestParam Integer pageNumber,
                                                       @RequestParam Integer pageSize) {
        return new ResponseEntity<>(service.getEmployees(pageNumber, pageSize), OK);
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
    private ResponseEntity<Employee> saveEmployee(@Valid @RequestBody Employee employee) {
        return new ResponseEntity<>(service.saveEmployee(employee), CREATED);
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
    public ResponseEntity<Employee> editEmployee(
            @PathVariable Long id,
            @RequestBody Employee employee) {
        employee.setId(id);
        return new ResponseEntity<>(service.updateEmployee(employee), OK);
    }

    @GetMapping("employees/filter/nameAndLocation")
    public ResponseEntity<List<Employee>> getByNameAndLocation(@RequestParam String name,
                                               @RequestParam String location) {
        return new ResponseEntity<>(service.getEmployeeByNameAndLocation(name, location), OK);
    }

    @GetMapping("employees/filter/nameKeyword")
    public ResponseEntity<List<Employee>> getByNameAndLocation(@RequestParam String keyword)
                                                {
        return new ResponseEntity<>(service.getEmployeeByKeyword(keyword), OK);
    }

}
