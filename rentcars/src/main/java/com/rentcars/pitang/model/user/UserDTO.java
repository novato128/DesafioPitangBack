package com.rentcars.pitang.model.user;

import java.time.LocalDate;
import java.util.List;

import com.rentcars.pitang.model.car.CarDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	
	@NotBlank 
	private String firstName; 
	@NotBlank 
	private String lastName; 
	@NotBlank
	@Email
	private String email; 
	private LocalDate birthday;
	@NotBlank 
	private String login; 
	@NotBlank 
	private String password;
	@NotBlank 
	private String phone;
	private List<CarDTO> cars;
}
