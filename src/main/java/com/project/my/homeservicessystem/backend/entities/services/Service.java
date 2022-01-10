package com.project.my.homeservicessystem.backend.entities.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String title;
    private double basePrice;
    private String description;
    @ManyToOne(optional = false)
    private ServiceCategory category;

    private Service(String title, double basePrice, String description, ServiceCategory category) {
        this(null, title, basePrice, description, category);
    }

    public static Service of(String title, ServiceCategory category) {
        return of(title, 0, category);
    }

    public static Service of(String title, double basePrice, ServiceCategory category) {
        return of(title, basePrice, "", category);
    }

    public static Service of(String title, double basePrice, String description, ServiceCategory category) {
        return new Service(title, basePrice, description, category);
    }
}
