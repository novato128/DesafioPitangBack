package com.rentcars.pitang.exception;

public class EmailAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmailAlreadyExistsException(String complemento) {
        super(complemento);
    }
}