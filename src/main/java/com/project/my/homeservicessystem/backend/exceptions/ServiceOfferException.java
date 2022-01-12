package com.project.my.homeservicessystem.backend.exceptions;

public class ServiceOfferException extends RuntimeException {

    public ServiceOfferException(String msg) {
        super(msg);
    }

    public ServiceOfferException(String msg, Throwable e) {
        super(msg, e);
    }
}
