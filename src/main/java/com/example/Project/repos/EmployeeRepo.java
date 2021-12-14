package com.example.Project.repos;

import com.example.Project.domains.Employee;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepo extends PagingAndSortingRepository<Employee, Long> {

    List<Employee> findByNameAndLocation(String name, String location);

    List<Employee>  findByNameContaining(String keyword, Sort sort);
}
