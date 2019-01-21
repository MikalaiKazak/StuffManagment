package com.nikolay.rest.errorHandler;

import java.util.List;
import org.springframework.http.HttpStatus;

public class ApiError {

    private HttpStatus status;
    private List<String> message;
    private String details;

    public ApiError(HttpStatus status, List<String> message, String details) {
        this.status = status;
        this.message = message;
        this.details = details;
    }

    public ApiError(HttpStatus status, List<String> message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public List<String> getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
