package com.project.my.homeservicessystem.backend.entities.users;

import com.project.my.homeservicessystem.backend.entities.services.Service;
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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"service_id", "customer_id", "provider_id"}))
public class UserFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    @ManyToOne(optional = false)
    private Customer customer;
    @ManyToOne(optional = false)
    private Provider provider;
    @ManyToOne(optional = false)
    private Service service;
    private int rate;

    private UserFeedback(String text, Customer customer, Provider provider, Service service, int rate) {
        this(null, text, customer, provider, service, rate);
    }

    public static UserFeedback of(Customer customer, Provider provider, Service service, int rate) {
        return of(customer, provider, service, rate, "");
    }

    public static UserFeedback of(Customer customer, Provider provider, Service service, int rate, String text) {
        return new UserFeedback(text, customer, provider, service, rate);
    }
}
