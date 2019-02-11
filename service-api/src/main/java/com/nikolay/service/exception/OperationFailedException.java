package com.nikolay.service.exception;

/**
 * The type Operation failed exception.
 */
public class OperationFailedException extends RuntimeException {

  /**
   * Instantiates a new Operation failed exception.
   *
   * @param message the message
   */
  public OperationFailedException(String message) {
    super(message);
  }

  /**
   * Instantiates a new Operation failed exception.
   */
  public OperationFailedException() {
  }
}
