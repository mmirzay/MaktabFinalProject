package com.project.my.homeservicessystem.backend.entities.users;

import com.project.my.homeservicessystem.backend.entities.services.Service;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Provider extends User {
    private String profilePhotoUrl;
    private long score;
    @ManyToMany
    private Set<Service> services;

    private Provider(String email, String password, Role role, String firstName, String lastName) {
        super(email, password, role, firstName, lastName);
        this.profilePhotoUrl = "";
        this.services = new HashSet<>();
    }

    public static Provider of(String email, String password, Role role) {
        return of(email, password, role, "", "");
    }

    public static Provider of(String email, String password, Role role, String firstName, String lastName) {
        return new Provider(email, password, role, firstName, lastName);
    }
}
