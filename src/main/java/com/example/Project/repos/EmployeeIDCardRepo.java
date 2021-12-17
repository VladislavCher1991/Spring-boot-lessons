package com.example.Project.repos;

import com.example.Project.domains.EmployeeIDCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeIDCardRepo extends JpaRepository <EmployeeIDCard, String> {

    EmployeeIDCard findByPassport (String passport);

}
