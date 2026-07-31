package com.iispl.exception;

public class ChequeNotFoundException extends RuntimeException {
	public ChequeNotFoundException(String message) {
        super(message);
    }
}
