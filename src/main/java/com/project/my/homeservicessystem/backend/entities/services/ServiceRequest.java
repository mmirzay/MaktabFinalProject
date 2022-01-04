package com.project.my.homeservicessystem.backend.entities.services;

import com.project.my.homeservicessystem.backend.entities.users.Customer;
import com.project.my.homeservicessystem.backend.entities.users.User;
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
public class ServiceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double price;
    private String description;
    private Date submitDate;
    private Date startDate;
    private String address;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Service service;
    private ServiceRequestStatus status = ServiceRequestStatus.UNDER_OFFERING;
}
