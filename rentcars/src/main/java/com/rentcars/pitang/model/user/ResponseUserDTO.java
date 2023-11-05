package com.rentcars.pitang.model.user;

import java.time.LocalDate;
import java.util.List;

import com.rentcars.pitang.model.car.Car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseUserDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private LocalDate birthday;
	private String login;
	private String password;
	private String phone;
	private List<Car> cars;
	
}
