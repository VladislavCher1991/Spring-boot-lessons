package com.example.Project.service;

import com.example.Project.domains.Employee;
import com.example.Project.domains.Role;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.DESC;


@Service
@RequiredArgsConstructor
public class EmployeeService implements UserDetailsService {

    private final RoleRepo roleRepo;
    private final EmployeeRepo employeeRepo;
    private final PasswordEncoder encoder;


    public List<Employee> getEmployees(int pageNumber, int pageSize) {
        Pageable pages = PageRequest.of(pageNumber, pageSize, DESC, "id");
        return employeeRepo.findAll(pages).getContent();
    }

    public Employee saveEmployee(Employee employee) {
        employee.setPassword(encoder.encode(employee.getPassword()));
        return employeeRepo.save(employee);
    }

    public Employee getOrDeleteEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepo.findById(id);
        if (employee.isEmpty()) return null;
        return employee.get();
    }

    public List<Employee> getEmployeeByNameAndLocation(String name, String location) {
        return employeeRepo.findByUsernameAndLocation(name, location);
    }

    public List<Employee> getEmployeeByKeyword(String keyword) {
        Sort sort = Sort.by(DESC, "id");
        return employeeRepo.findByUsernameContaining(keyword, sort);
    }

    public List<Employee> getEmployeeByNameOrLocation(String name, String location) {
        return employeeRepo.getEmployeesByNameAndLocation(name, location);
    }

    public Integer deleteByName(String name) {
        return employeeRepo.deleteEmployeeByusername(name);
    }

    public Employee validateEmployeeByUsername(String username) {
        Employee employee = employeeRepo.findByUsername(username);
        if (employee == null) throw new UsernameNotFoundException("No employee with such username exists in DB!");
        return employee;
    }

    public void saveRole (Role role) {
        roleRepo.save(role);
    }

    public Role validateRoleByRoleName(String roleName) {
        Role role = roleRepo.findByName(roleName);
        if (role == null) throw new UsernameNotFoundException("No role with such name exists in DB!");
        return role;
    }

    public void addRolesToEmployee(String username, String roleName) {
        Employee employee = validateEmployeeByUsername(username);
        Role role = validateRoleByRoleName(roleName);

        employee.getRoles().add(role);
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
}


