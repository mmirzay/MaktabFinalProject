package com.project.my.homeservicessystem.backend.entities.services;

import com.project.my.homeservicessystem.backend.entities.users.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"status", "customer_id", "service_id"}))
public class ServiceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double price;
    private String description;
    private Date submitDate;
    private Date startDate;
    private String address;
    @ManyToOne(optional = false)
    private Customer customer;
    @ManyToOne(optional = false)
    private Service service;
    private ServiceRequestStatus status;

    private ServiceRequest(double price, String description, Date startDate, String address, Customer customer, Service service) {
        this(null, price, description, new Date(), startDate, address, customer, service, ServiceRequestStatus.UNDER_OFFERING);
    }

    public static ServiceRequest of(Customer customer, Service service, double price, Date startDate) {
        return of(customer, service, price, startDate, "", "");
    }

    public static ServiceRequest of(Customer customer, Service service, double price, Date startDate, String description, String address) {
        return new ServiceRequest(price, description, startDate, address, customer, service);
    }
}
