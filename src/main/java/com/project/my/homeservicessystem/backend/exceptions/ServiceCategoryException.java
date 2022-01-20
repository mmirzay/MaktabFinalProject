package com.project.my.homeservicessystem.backend.exceptions;

public class ServiceCategoryException extends Exception {

    public ServiceCategoryException(String msg) {
        super(msg);
    }

    public ServiceCategoryException(String msg, Throwable e) {
        super(msg, e);
    }
}
