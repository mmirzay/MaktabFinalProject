package com.project.my.homeservicessystem.backend.entities.services;

import java.util.Date;

import HomeServicesSystem.backend.entities.users.User;

public class ServiceRequest {
	private Long id;
	private double price;
	private String description;
	private Date submitionDate;
	private Date startDate;
	private String address;
	private User customer;
	private Service service;
	private ServiceRequestStatus status;
}
