package com.nikolay.rest.errorHandler;

import org.springframework.http.HttpStatus;

public class ApiError {

    private HttpStatus status;
    private String msg;

    public ApiError(HttpStatus status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
