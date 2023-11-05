package com.rentcars.pitang.exception;

public class TokenNotSendException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TokenNotSendException(String complemento) {
        super(complemento);
    }
}
