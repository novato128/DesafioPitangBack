package com.rentcars.pitang.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rentcars.pitang.config.security.infra.TokenService;
import com.rentcars.pitang.model.auth.AuthenticationDTO;
import com.rentcars.pitang.model.auth.LoginResponseDTO;
import com.rentcars.pitang.model.error.Erro;
import com.rentcars.pitang.model.user.ResponseUserDTO;
import com.rentcars.pitang.model.user.User;
import com.rentcars.pitang.model.user.UserDTO;
import com.rentcars.pitang.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@SuppressWarnings("rawtypes")
@ApiResponses(value = {
		@ApiResponse(responseCode = "400", description = "Foi gerada uma exceção", 
				content = @Content(schema = @Schema(implementation = Erro.class)))
})
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	
	@Autowired
	private UserService userService;
	
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

	@Operation(summary = "Retorna o token de acesso da API (JWT) com as informações do usuário logado.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna Sucesso."),
	})
	@PostMapping("/signin")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO("Bearer "+ token));
	}

	@Operation(summary = "Lista todos os usuários.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna Sucesso."),
	})
	
	@GetMapping
	public ResponseEntity<List<ResponseUserDTO>> getAllUsers() {
		
		List<ResponseUserDTO> listUser = this.userService.getAllUsersResponse();
		return ResponseEntity.ok(listUser);
	}

	@Operation(summary = "Cadastra um novo usuário.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna Sucesso."),
	})
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseUserDTO saveUser(@RequestBody @Valid UserDTO user) {
		return this.userService.saveUser(user);
	}
	
	@Operation(summary = "Busca um usuário pelo id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna Sucesso."),
	})
	@GetMapping("/{id}")
	public ResponseEntity<ResponseUserDTO> getUser(@PathVariable Long id) {
		
		ResponseUserDTO ResponseUser = this.userService.getUser(id);
		return ResponseEntity.ok(ResponseUser);
	}
	
	@Operation(summary = "Remove um usuário pelo id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna Sucesso."),
	})
	@DeleteMapping("/{id}")
	public ResponseEntity deleteUser(@PathVariable Long id) {
		
		this.userService.deleteUser(id);
		return ResponseEntity.ok(null);
	}
	
	@Operation(summary = "Atualiza um usuário pelo id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna Sucesso."),
	})
	@PutMapping("/{id}")
	public ResponseEntity updateUser(@PathVariable Long id, @RequestBody UserDTO user) {
		
		ResponseUserDTO ResponseUser = this.userService.updateUser(id, user);
		return ResponseEntity.ok(ResponseUser);
	}
}
