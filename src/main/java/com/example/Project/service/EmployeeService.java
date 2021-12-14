package com.example.Project.service;

import com.example.Project.domains.Employee;
import com.example.Project.repos.EmployeeRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class EmployeeService {

    private final EmployeeRepo repo;

    public EmployeeService(EmployeeRepo repo) {
        this.repo = repo;
    }

    public List<Employee> getEmployees(int pageNumber, int pageSize) {
        Pageable pages = PageRequest.of(pageNumber, pageSize);
        return repo.findAll(pages).getContent();
    }

    public Employee saveEmployee(Employee employee) {
        return repo.save(employee);
    }

    public Employee getOrDeleteEmployeeById(Long id) {
        Optional<Employee> employee = repo.findById(id);
        if (employee.isEmpty()) return null;
        return employee.get();
    }

    public Employee updateEmployee(Employee employee) {
        return  repo.save(employee);
    }

    public  List<Employee> getEmployeeByNameAndLocation(String name, String location){
        return repo.findByNameAndLocation(name, location);
    }

    public  List<Employee> getEmployeeByKeyword(String keyword){
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return repo.findByNameContaining(keyword, sort);
    }
}


