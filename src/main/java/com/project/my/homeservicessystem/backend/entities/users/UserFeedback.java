package com.project.my.homeservicessystem.backend.entities.users;

import com.project.my.homeservicessystem.backend.entities.services.Service;

public class UserFeedback {
	private Long id;
	private String text;
	private User customer;
	private User serviceProvider;
	private Service service;
	private int rate;
}
