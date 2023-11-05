package com.rentcars.pitang.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rentcars.pitang.config.security.infra.TokenService;
import com.rentcars.pitang.model.car.Car;
import com.rentcars.pitang.model.car.CarDTO;
import com.rentcars.pitang.model.error.Erro;
import com.rentcars.pitang.model.user.CarByUser;
import com.rentcars.pitang.service.CarService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/cars")
@SuppressWarnings("rawtypes")
@ApiResponses(value = {
		@ApiResponse(responseCode = "400", description = "Foi gerada uma exceção", 
				content = @Content(schema = @Schema(implementation = Erro.class)))
})
@CrossOrigin("*")
public class CarController {
	
	@Autowired
	private CarService carService;
	
	@Autowired
	private TokenService tokenService;

	@Operation(summary = "Lista todos os carros do usuário logado.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna Sucesso."),
	})
	@GetMapping
	public ResponseEntity<List<Car>> getAllcars(@RequestHeader(value="Authorization") String jwtToken) {
		String login = tokenService.validateToken(jwtToken.replace("Bearer ", ""));
		List<Car> listCars = this.carService.getAllcars(login);
		return ResponseEntity.ok(listCars);
	}
	
	@Operation(summary = "Cadastra um novo carro para o usuário logado.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna Sucesso."),
	})
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public void saveCar(@RequestHeader(value="Authorization") String jwtToken, @RequestBody CarDTO car) {
		String login = tokenService.validateToken(jwtToken.replace("Bearer ", ""));
		this.carService.saveCar(car, login);
	}
	
	@Operation(summary = "Busca um carro do usuário logado pelo id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna Sucesso."),
	})
	@GetMapping("/{id}")
	public ResponseEntity getCar(@RequestHeader(value="Authorization") String jwtToken, @PathVariable Long id) {
		String login = tokenService.validateToken(jwtToken.replace("Bearer ", ""));
		Car ResponseCar = this.carService.getCar(id, login);
		return ResponseEntity.ok(ResponseCar);
	}
	
	@Operation(summary = "Remove um carro do usuário logado pelo id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna Sucesso."),
	})
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public ResponseEntity deleteCar(@RequestHeader(value="Authorization") String jwtToken, @PathVariable Long id) {
		String login = tokenService.validateToken(jwtToken.replace("Bearer ", ""));
		this.carService.deleteCar(id, login);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(summary = "Atualiza um carro do usuário logado pelo id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna Sucesso."),
	})
	@PutMapping("/{id}")
	public ResponseEntity updateCar(@RequestHeader(value="Authorization") String jwtToken, @PathVariable Long id, @RequestBody CarDTO car) {
		String login = tokenService.validateToken(jwtToken.replace("Bearer ", ""));
		this.carService.updateCar(id, car, login);
		return ResponseEntity.ok(null);
	}
	
	@Operation(summary = "Busca Lista de carros ordenada pelo usuario e uso do carro.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna Sucesso."),
	})
	@GetMapping("/car-count")
	public ResponseEntity getCarCount(
			@RequestHeader(value="Authorization") String jwtToken) {
		tokenService.validateToken(jwtToken.replace("Bearer ", ""));
		List<CarByUser> ResponseCar = this.carService.getCarCount();
		return ResponseEntity.ok(ResponseCar);
	}
}
