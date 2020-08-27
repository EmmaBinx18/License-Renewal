package com.bbd.licenscerenewal.exceptionHandling;

public class ErrorMessage {
    private String message;
    private int status;

    public ErrorMessage(int status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public int getstatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setstatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}