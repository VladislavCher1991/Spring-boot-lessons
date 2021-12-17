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

    List<Employee> findByUsernameAndEmail(String name, String email);

    List<Employee>  findByUsernameContaining(String keyword, Sort sort);

    @Query("FROM Employee WHERE username = :username OR email = :email")
    List<Employee> getEmployeesByNameAndEmail (@Param("username") String asd, String email);

    @Transactional
    @Modifying
    @Query("DELETE FROM Employee WHERE username = :username")
    Integer deleteEmployeeByUsername(String username);

    Employee findByUsername(String username);
}

