package com.rentcars.pitang.exception;

public class LoginAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LoginAlreadyExistsException(String complemento) {
        super(complemento);
    }
}
