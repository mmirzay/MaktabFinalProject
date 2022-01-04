package com.project.my.homeservicessystem.backend.entities.users;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
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
    private UserStatus status = UserStatus.NEW;
    private Date registerDate = new Date();
    @ManyToMany
    private Set<Role> roles;
    private double credit;
}
