package com.nikolay.service.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmployeeNotFoundException extends RuntimeException {

    public static final Logger LOGGER = LogManager.getLogger();

    public EmployeeNotFoundException() {
    }

    public EmployeeNotFoundException(String message) {
        super(message);
        LOGGER.error(message);
    }
}
