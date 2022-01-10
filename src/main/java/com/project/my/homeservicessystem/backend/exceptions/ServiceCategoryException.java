package com.project.my.homeservicessystem.backend.exceptions;

public class ServiceCategoryException extends RuntimeException {

    public ServiceCategoryException(String msg) {
        super(msg);
    }

    public ServiceCategoryException(String msg, Throwable e) {
        super(msg, e);
    }
}
