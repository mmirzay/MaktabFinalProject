package com.project.my.homeservicessystem.backend.entities.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    private UserStatus status;
    private Date registerDate;
    @ManyToMany
    private Set<Role> roles;
    private double credit;

    public User(String email, String password, Role role, String firstName, String lastName) {
        this(null, firstName, lastName, email, password, UserStatus.NEW, new Date(), new HashSet<>(List.of(role)), 0);
    }
}
