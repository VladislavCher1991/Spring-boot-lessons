package com.example.Project.repos;

import com.example.Project.domains.Employee;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EmployeeRepo extends PagingAndSortingRepository<Employee, Long> {

    List<Employee> findByNameAndLocation(String name, String location);

    List<Employee>  findByNameContaining(String keyword, Sort sort);

    @Query("FROM Employee WHERE name = :name OR location = :location")
    List<Employee> getEmployeesByNameAndLocation (@Param("name") String asd, String location);

    @Transactional
    @Modifying
    @Query("DELETE FROM Employee WHERE name = :name")
    Integer deleteEmployeeByName(String name);
}
