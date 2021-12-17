package com.example.Project.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table (name = "employee_id_card")
public class EmployeeIDCard {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private Integer age;
    private String address;

    @Column (unique = true)
    private String passport;

    @OneToOne(mappedBy = "employeeIDCard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Employee employee;

    public EmployeeIDCard(String name, String surname, Integer age, String address, String passport) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.address = address;
        this.passport = passport;
    }

    @Override
    public String toString() {
        return "EmployeeIDCard{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", passport='" + passport + '\'' +
                '}';
    }
}
