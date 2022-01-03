package com.project.my.homeservicessystem.backend.entities.users;

import java.util.Date;

public abstract class User {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private UserStatus status;
	private Date registerDate;
	private UserRole role;
	private double credit;
}
