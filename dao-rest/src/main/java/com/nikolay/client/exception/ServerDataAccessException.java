package com.nikolay.client.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type Server data access exception.
 */
public class ServerDataAccessException extends RuntimeException {

  /**
   * The constant LOGGER.
   */
  public static final Logger LOGGER = LogManager.getLogger();

  /**
   * Instantiates a new Server data access exception.
   */
  public ServerDataAccessException() {
    LOGGER.error("ServerDataAccessException()");
  }

  /**
   * Instantiates a new Server data access exception.
   *
   * @param message the message
   */
  public ServerDataAccessException(String message) {
    super(message);
    LOGGER.error(message);
  }
}
