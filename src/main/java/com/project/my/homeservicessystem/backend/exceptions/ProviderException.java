package com.project.my.homeservicessystem.backend.exceptions;

public class ProviderException extends RuntimeException {

    public ProviderException(String msg) {
        super(msg);
    }

    public ProviderException(String msg, Throwable e) {
        super(msg, e);
    }
}
