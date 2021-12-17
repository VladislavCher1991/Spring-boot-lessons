package com.example.Project.domains;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table
public class Employee {



    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "field 'username' must not be null!")
    private String username;

    private String password;

    @Email (message = "Please enter the valid email address!")
    private String email;

    @OneToOne (cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn (name = "employee_id_card_passport")
    private EmployeeIDCard employeeIDCard;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    @ManyToMany
    private Collection<Role> roles;

    public Employee(String username, String password, String email,  Collection<Role> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.employeeIDCard = null;
        this.roles = roles;
    }
}
