package com.project.my.homeservicessystem.backend.entities.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ServiceProvider extends User {
	private String profilePhotoUrl;
	private long score;
}
