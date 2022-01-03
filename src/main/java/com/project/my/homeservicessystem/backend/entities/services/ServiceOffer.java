package com.project.my.homeservicessystem.backend.entities.services;

import com.project.my.homeservicessystem.backend.entities.users.ServiceProvider;

import java.util.Date;

public class ServiceOffer {
	private Long id;
	private Date submitionDate;
	private Integer startHour;
	private ServiceProvider provider;
	private ServiceRequest request;
}
