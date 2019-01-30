package com.nikolay.rest.errorhandler;

import java.util.List;
import org.springframework.http.HttpStatus;

/**
 * The type Api error.
 */
public class ApiError {

  private HttpStatus status;
  private List<String> message;
  private String details;

  /**
   * Instantiates a new Api error.
   *
   * @param status the status
   * @param message the message
   * @param details the details
   */
  public ApiError(HttpStatus status, List<String> message, String details) {
    this.status = status;
    this.message = message;
    this.details = details;
  }

  /**
   * Instantiates a new Api error.
   *
   * @param status the status
   * @param message the message
   */
  public ApiError(HttpStatus status, List<String> message) {
    this.status = status;
    this.message = message;
  }

  /**
   * Gets status.
   *
   * @return the status
   */
  public HttpStatus getStatus() {
    return status;
  }

  /**
   * Gets message.
   *
   * @return the message
   */
  public List<String> getMessage() {
    return message;
  }

  /**
   * Gets details.
   *
   * @return the details
   */
  public String getDetails() {
    return details;
  }

  @Override
  public String toString() {
    return super.toString();
  }
}
