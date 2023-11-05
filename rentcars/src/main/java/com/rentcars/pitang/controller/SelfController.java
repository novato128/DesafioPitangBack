package com.rentcars.pitang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentcars.pitang.config.security.infra.TokenService;
import com.rentcars.pitang.model.error.Erro;
import com.rentcars.pitang.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api")
@ApiResponses(value = {
		@ApiResponse(responseCode = "400", description = "Foi gerada uma exceção", 
				content = @Content(schema = @Schema(implementation = Erro.class)))
})
@CrossOrigin(origins = "http://localhost:4200")
public class SelfController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenService tokenService;
	
	@Operation(summary = "Retornar as informações do usuário logado.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna Sucesso."),
	})
	@GetMapping("/me")
	public ResponseEntity<UserDetails> getUserByToken(@RequestHeader(value="Authorization") String jwtToken) {
		
		String login = tokenService.validateToken(jwtToken.replace("Bearer ", ""));
		UserDetails ResponseUser = this.userService.findByLogin(login);
		return ResponseEntity.ok(ResponseUser);
	}
}
