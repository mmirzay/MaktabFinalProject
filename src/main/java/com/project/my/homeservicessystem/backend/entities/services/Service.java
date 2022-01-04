package com.project.my.homeservicessystem.backend.entities.services;

import com.project.my.homeservicessystem.backend.entities.users.ServiceProvider;
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
public class Service {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private double basePrice;
	private String description;
	@ManyToOne
	private ServiceProvider provider;
	@ManyToOne
	private ServiceCategory category;
}
