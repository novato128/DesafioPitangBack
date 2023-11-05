package com.rentcars.pitang.exception;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String complemento) {
        super(complemento);
    }
}