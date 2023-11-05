package com.rentcars.pitang.exception;

public class ExpiredTokenException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ExpiredTokenException(String complemento) {
        super(complemento);
    }
}