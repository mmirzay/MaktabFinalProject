package com.project.my.homeservicessystem.backend.exceptions;

public class UserFeedBackException extends Exception {

    public UserFeedBackException(String msg) {
        super(msg);
    }

    public UserFeedBackException(String msg, Throwable e) {
        super(msg, e);
    }
}
