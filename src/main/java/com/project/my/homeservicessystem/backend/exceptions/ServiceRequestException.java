package com.project.my.homeservicessystem.backend.exceptions;

public class ServiceRequestException extends RuntimeException {

    public ServiceRequestException(String msg) {
        super(msg);
    }

    public ServiceRequestException(String msg, Throwable e) {
        super(msg, e);
    }
}
