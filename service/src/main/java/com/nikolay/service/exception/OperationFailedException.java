package com.nikolay.service.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type Operation failed exception.
 */
public class OperationFailedException extends RuntimeException {

    /**
     * The constant LOGGER.
     */
    public static final Logger LOGGER = LogManager.getLogger();

    /**
     * Instantiates a new Operation failed exception.
     *
     * @param message the message
     */
    public OperationFailedException(String message) {
        super(message);
        LOGGER.error(message);
    }

    /**
     * Instantiates a new Operation failed exception.
     */
    public OperationFailedException() {
    }
}
