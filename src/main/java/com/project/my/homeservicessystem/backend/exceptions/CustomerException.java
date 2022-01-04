package com.project.my.homeservicessystem.backend.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class CustomerException extends RuntimeException {

    public CustomerException(String msg) {
        super(msg);
    }

    public CustomerException(String msg, Throwable e) {
        super(msg, e);
    }
}
