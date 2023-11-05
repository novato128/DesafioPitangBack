package com.rentcars.pitang.model.car;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CAR_ID")
	private Long id;
	@Column(unique=true)
	private String licensePlate;
	@Column(name="CAR_YEAR")
	private int year;
	private String model;
	private String color;
}
