package com.project.my.homeservicessystem.backend.entities.users;

import com.project.my.homeservicessystem.backend.entities.services.Service;

import java.util.Set;

public class ServiceProvider extends User {
	private String profilePhotoUrl;
	private long score;
	private Set<Service> services;
}
