package com.project.my.homeservicessystem.backend.exceptions;

public class RoleException extends RuntimeException {
    public RoleException(String msg, Throwable e) {
        super(msg, e);
    }

    public RoleException(String msg) {
        super(msg);
    }
}
