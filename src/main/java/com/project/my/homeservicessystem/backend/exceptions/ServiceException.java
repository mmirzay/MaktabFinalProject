package com.project.my.homeservicessystem.backend.exceptions;

public class ServiceException extends RuntimeException {

    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(String msg, Throwable e) {
        super(msg, e);
    }
}
