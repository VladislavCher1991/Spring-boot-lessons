package com.example.Project;

import com.example.Project.domains.Department;
import com.example.Project.domains.Employee;
import com.example.Project.domains.EmployeeIDCard;
import com.example.Project.domains.Role;
import com.example.Project.service.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

	@Bean
	CommandLineRunner runner (EmployeeService service){
		return args -> {
			service.saveRole(new Role("ROLE_ADMIN"));
			service.saveRole(new Role("ROLE_USER"));

			Department department = new Department("IT", new ArrayList<>());

			EmployeeIDCard idCard1 = new EmployeeIDCard("name1", "surname1", 22, "address1", "AAAABBBB123");
			EmployeeIDCard idCard2 = new EmployeeIDCard("name2", "surname2", 44, "address2", "CCCCDDDD456");

			Employee admin = new Employee("admin", "1111", "someEmail@mail.com", new ArrayList<>());
			Employee user = new Employee("user", "1111", "someEmail@mail.com", new ArrayList<>());

			service.saveEmployee(admin, idCard1);
			service.saveEmployee(user, idCard2);

			service.saveDepartment(department);

			service.addEmployeeToDepartment("admin", "IT");
			service.addEmployeeToDepartment("user", "IT");

			service.addRoleToEmployee("admin", "ROLE_ADMIN");
			service.addRoleToEmployee("admin", "ROLE_USER");
			service.addRoleToEmployee("user", "ROLE_USER");


		};
	}
}
