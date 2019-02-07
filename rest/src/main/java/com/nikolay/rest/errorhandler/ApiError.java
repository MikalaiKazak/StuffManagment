package com.nikolay.rest.errorhandler;

import java.util.List;

/**
 * The type Api error.
 */
public class ApiError {

  private List<String> message;
  private String details;

  /**
   * Instantiates a new Api error.
   *
   * @param message the message
   * @param details the details
   */
  public ApiError(List<String> message, String details) {
    this.message = message;
    this.details = details;
  }

  /**
   * Instantiates a new Api error.
   */
  public ApiError() {
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
   * Sets message.
   *
   * @param message the message
   */
  public void setMessage(List<String> message) {
    this.message = message;
  }

  /**
   * Gets details.
   *
   * @return the details
   */
  public String getDetails() {
    return details;
  }

  /**
   * Sets details.
   *
   * @param details the details
   */
  public void setDetails(String details) {
    this.details = details;
  }

  @Override
  public String toString() {
    return super.toString();
  }
}
