package com.iispl.exception;

public class DuplicateChequeNumberException extends RuntimeException {

    public DuplicateChequeNumberException(String message) {
        super(message);
    }

}