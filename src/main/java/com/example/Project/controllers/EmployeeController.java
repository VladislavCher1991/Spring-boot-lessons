package com.example.Project.controllers;

import com.example.Project.domains.Department;
import com.example.Project.domains.Employee;
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

    private final DepartmentRepo depRepo;
    private final EmployeeService service;

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;


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
        Department depFromDB = depRepo.findByName(employee.getDepartment());
        if (depFromDB == null) {
            Department department = new Department(employee.getDepartment());
            department.setEmployees(new ArrayList<>());
            department.getEmployees().add(employee);
            depRepo.save(department);
        } else depFromDB.getEmployees().add(employee);
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
    public ResponseEntity<String> editEmployee(
            @PathVariable Long id,
            @RequestBody Employee employee) {
        employee.setId(id);
        service.updateEmployee(employee);
        if (depRepo.findByName(employee.getDepartment()) == null) return new ResponseEntity<>
                ("department with name: " +employee.getDepartment() + " didn't found in the database", BAD_REQUEST);
        return new ResponseEntity<>("Employee with id: " + employee.getId() + " has been successfully edited", OK);
    }

    @GetMapping("employees/filter/nameAndLocation")
    public ResponseEntity<List<Employee>> getByNameAndLocation(@RequestParam String name,
                                                               @RequestParam String location) {
        return new ResponseEntity<>(service.getEmployeeByNameAndLocation(name, location), OK);
    }

    @GetMapping("employees/filter/nameKeyword")
    public ResponseEntity<List<Employee>> getByNameAndLocation(@RequestParam String keyword) {
        return new ResponseEntity<>(service.getEmployeeByKeyword(keyword), OK);
    }

    @GetMapping("employees/{name}/{location}")
    public ResponseEntity<List<Employee>> getByNameOrLocation(@PathVariable String name,
                                                              @PathVariable String location) {
        return new ResponseEntity<>(service.getEmployeeByNameOrLocation(name, location), OK);
    }

    @DeleteMapping("employees/delete/{name}")
    public ResponseEntity<String> deleteByName(@PathVariable String name) {
        return new ResponseEntity<>(service.deleteByName(name) + " amount of records was deleted", NO_CONTENT);
    }

}
