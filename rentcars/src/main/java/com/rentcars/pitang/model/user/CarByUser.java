package com.rentcars.pitang.model.user;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rentcars.pitang.model.car.CarByUsage;

import lombok.Data;

@Data
public class CarByUser {

	private String login;
	private List<CarByUsage> listaCarro;
	@JsonIgnore
	private Integer totalUsages;
	
}
