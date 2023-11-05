package com.rentcars.pitang.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.rentcars.pitang.model.error.Erro;

@RestControllerAdvice
public class ControllerException {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Erro methodArgumentNotValid(MethodArgumentNotValidException exception) {
		Erro erro = new Erro("Missing fields", 1);
		return erro;
	}
	
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(TokenNotSendException.class)
	public Erro erroWrongCredentials(TokenNotSendException ex) {
		Erro erro = new Erro("Unauthorized", 2);
		return erro;
	}
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(TokenExpiredException.class)
	public Erro expiredToken(TokenExpiredException ex) {
		Erro erro = new Erro("Unauthorized - invalid session", 3);
		return erro;
	}
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public Erro expiredToken(DataIntegrityViolationException ex) {
		Erro erro = new Erro("License plate already exists", 4);
		return erro;
	}
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(UserNotFoundException.class)
	public Erro userNotFoundException(UserNotFoundException ex) {
		Erro erro = new Erro("User not found", 5);
		return erro;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(EmailAlreadyExistsException.class)
	public Erro userNotFoundException(EmailAlreadyExistsException ex) {
		Erro erro = new Erro("Email already exists", 6);
		return erro;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(LoginAlreadyExistsException.class)
	public Erro userNotFoundException(LoginAlreadyExistsException ex) {
		Erro erro = new Erro("Login already exists", 7);
		return erro;
	}
	
}
