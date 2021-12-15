package com.example.Project.repos;

import com.example.Project.domains.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepo extends JpaRepository<Department, Long> {

    Department findByName(String name);
}
