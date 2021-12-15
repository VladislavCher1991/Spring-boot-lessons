package com.example.Project.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table (name = "tbl_department")
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany (cascade = CascadeType.ALL)
    @JoinColumn (name = "employee_id")

    private List<Employee> employees;

    public Department(String name) {
        this.name = name;
    }

}
