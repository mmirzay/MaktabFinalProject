package com.project.my.homeservicessystem.backend.entities.users;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
public class Customer extends User {
    private Customer(String email, String password, Role role) {
        this(email, password, role, "", "");
    }

    private Customer(String email, String password, Role role, String firstName, String lastName) {
        super(email, password, role, firstName, lastName);
    }

    public static Customer of(String email, String password, Role role) {
        return new Customer(email, password, role);
    }

    public static Customer of(String email, String password, Role role, String firstName, String lastName) {
        return new Customer(email, password, role, firstName, lastName);
    }
}
