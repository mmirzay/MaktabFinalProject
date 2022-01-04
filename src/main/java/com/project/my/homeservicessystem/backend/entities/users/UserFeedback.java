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
public class UserFeedback {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String text;
	@ManyToOne
	private Customer customer;
	@OneToOne
	private Service service;
	private int rate;
}
