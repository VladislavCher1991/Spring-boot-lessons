package com.example.Project.service;

import com.example.Project.domains.Department;
import com.example.Project.domains.Employee;
import com.example.Project.domains.EmployeeIDCard;
import com.example.Project.domains.Role;
import com.example.Project.repos.DepartmentRepo;
import com.example.Project.repos.EmployeeRepo;
import com.example.Project.repos.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.DESC;


@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeService implements UserDetailsService {

    private final RoleRepo roleRepo;
    private final EmployeeRepo employeeRepo;
    private final PasswordEncoder encoder;
    private final DepartmentRepo departmentRepo;


    public List<Employee> getPagesOfEmployees(int pageNumber, int pageSize) {
        Pageable pages = PageRequest.of(pageNumber, pageSize, DESC, "id");
        return employeeRepo.findAll(pages).getContent();
    }

    public Employee saveEmployee(Employee employee, EmployeeIDCard idCard) {
        employee.setPassword(encoder.encode(employee.getPassword()));
        employee.setEmployeeIDCard(idCard);
        return employeeRepo.save(employee);
    }

    public Employee getOrDeleteEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepo.findById(id);
        if (employee.isEmpty()) return null;
        return employee.get();
    }

    public List<Employee> getEmployeeByNameAndEmail(String name, String email) {
        return employeeRepo.findByUsernameAndEmail(name, email);
    }

    public List<Employee> getEmployeeByKeyword(String keyword) {
        Sort sort = Sort.by(DESC, "id");
        return employeeRepo.findByUsernameContaining(keyword, sort);
    }

    public List<Employee> getEmployeeByNameOrEmail(String name, String email) {
        return employeeRepo.getEmployeesByNameAndEmail(name, email);
    }

    public Integer deleteByName(String name) {
        return employeeRepo.deleteEmployeeByUsername(name);
    }

    public Employee validateEmployeeByUsername(String username) {
        Employee employee = employeeRepo.findByUsername(username);
        if (employee == null) throw new UsernameNotFoundException("No employee with such username exists in DB!");
        return employee;
    }

    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    public Role validateRoleByName(String roleName) {
        Role role = roleRepo.findByName(roleName);
        if (role == null) throw new UsernameNotFoundException("No role with such name exists in DB!");
        return role;
    }

    public void addRoleToEmployee(String username, String roleName) {
        validateEmployeeByUsername(username)
                .getRoles()
                .add(validateRoleByName(roleName));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = validateEmployeeByUsername(username);

        String securityUsername = employee.getUsername();
        String securityPassword = employee.getPassword();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        employee.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

        return new User(securityUsername, securityPassword, authorities);
    }

    public Department saveDepartment(Department department) {
        return departmentRepo.save(department);
    }

    public Department validateDepartmentByName(String departmentName) {
        Department department = departmentRepo.findByName(departmentName);
        if (department == null) throw new UsernameNotFoundException("No department with such name exists in DB!");
        return department;
    }

    public void addEmployeeToDepartment(String username, String departmentName) {
        validateDepartmentByName(departmentName)
                .getEmployees()
                .add(validateEmployeeByUsername(username));
    }

}


