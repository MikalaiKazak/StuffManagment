package com.nikolay.webapp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nikolay.client.exception.ServerDataAccessException;

/**
 * The type Error controller.
 */
@ControllerAdvice
public class ErrorController extends RuntimeException {

  /**
   * The constant LOGGER.
   */
  public static final Logger LOGGER = LogManager.getLogger();

  /**
   * Handler illegal argument exception string.
   *
   * @param ex the ex
   * @param model the model
   * @return the string
   */
  @ExceptionHandler(IllegalArgumentException.class)
  public final String handlerIllegalArgumentException(final IllegalArgumentException ex,
      final Model model) {
    LOGGER.debug("exceptionHandler() msg = {}", ex.getLocalizedMessage());
    model.addAttribute("Text", ex.getMessage());
    return "errorPages/error400";
  }

  /**
   * Handler null pointer exception string.
   *
   * @param ex the ex
   * @param model the model
   * @return the string
   */
  @ExceptionHandler(NullPointerException.class)
  public final String handlerNullPointerException(final NullPointerException ex,
      final Model model) {
    LOGGER.debug("exceptionHandler() msg = {}", ex.getLocalizedMessage());
    model.addAttribute("Text", ex.getMessage());
    return "errorPages/error500";
  }

  /**
   * Handler server data access exception string.
   *
   * @param ex the ex
   * @param model the model
   * @return the string
   */
  @ExceptionHandler(ServerDataAccessException.class)
  public final String handlerServerDataAccessException(final ServerDataAccessException ex,
      final Model model) {
    LOGGER.debug("exceptionHandler() msg = {}", ex.getLocalizedMessage());
    model.addAttribute("Text", ex.getMessage());
    return "errorPages/error400";
  }

  /**
   * Handler exception string.
   *
   * @param ex the ex
   * @param model the model
   * @return the string
   */
  @ExceptionHandler(Exception.class)
  public final String handlerException(final Exception ex,
      final Model model) {
    LOGGER.debug("exceptionHandler() msg = {}", ex.getLocalizedMessage());
    model.addAttribute("Text", ex.getLocalizedMessage());
    return "errorPages/generalErrorPage";
  }

  /**
   * Handle error 405 string.
   *
   * @param ex the ex
   * @param model the model
   * @return the string
   */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public String handleError405(final HttpRequestMethodNotSupportedException ex,
      final Model model) {
    LOGGER.debug("handleError405() msg = {}", ex.getLocalizedMessage());
    model.addAttribute("Text", ex.getLocalizedMessage());
    return "errorPages/error405";
  }

}