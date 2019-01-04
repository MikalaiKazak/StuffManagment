package com.nikolay.service.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DepartmentNotFoundException extends RuntimeException {

    public static final Logger LOGGER = LogManager.getLogger();

    public DepartmentNotFoundException(String message) {
        super(message);
        LOGGER.error(message);
    }

    public DepartmentNotFoundException() {
    }
}
