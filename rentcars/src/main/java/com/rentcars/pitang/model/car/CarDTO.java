package com.rentcars.pitang.model.car;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
	
	@NotEmpty 
	private int year;
	@NotBlank 
	private String licensePlate; 
	@NotBlank 
	private String model; 
	@NotBlank 
	private String color;

}
