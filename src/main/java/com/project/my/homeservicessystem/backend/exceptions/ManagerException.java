package com.project.my.homeservicessystem.backend.exceptions;

public class ManagerException extends RuntimeException{
    public ManagerException(Throwable e){
        super(e);
    }

    @Override
    public String getLocalizedMessage() {
        return getCause().getLocalizedMessage();
    }
}
