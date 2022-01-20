package com.project.my.homeservicessystem.backend.exceptions;

public class CustomerException extends Exception {

    public CustomerException(String msg) {
        super(msg);
    }

    public CustomerException(String msg, Throwable e) {
        super(msg, e);
    }
}
