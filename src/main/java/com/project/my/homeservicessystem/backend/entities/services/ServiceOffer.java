package com.project.my.homeservicessystem.backend.entities.services;

import com.project.my.homeservicessystem.backend.entities.users.Provider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"provider_id", "request_id", "status"}))
public class ServiceOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date submitDate;
    private int startHour;
    private double price;
    private int durationInHours;
    @ManyToOne(optional = false)
    private Provider provider;
    @ManyToOne(optional = false)
    private ServiceRequest request;
    private ServiceOfferStatus status;
    private Date statusDate;

    private ServiceOffer(Date submitDate, int startHour, double price, int durationInHours, Provider provider, ServiceRequest request) {
        this(null, submitDate, startHour, price, durationInHours, provider, request, null, null);
        setStatus(ServiceOfferStatus.UNDER_ACCEPTING);
    }

    public static ServiceOffer of(Provider provider, ServiceRequest request, double price) {
        return of(provider, request, price, 0, 0);
    }

    public static ServiceOffer of(Provider provider, ServiceRequest request, double price, int startHour) {
        return of(provider, request, price, startHour, 0);
    }

    public static ServiceOffer of(Provider provider, ServiceRequest request, double price, int startHour, int durationInHours) {
        return new ServiceOffer(new Date(), startHour, price, durationInHours, provider, request);
    }

    public void setStatus(ServiceOfferStatus status) {
        this.status = status;
        this.statusDate = new Date();
    }
}
