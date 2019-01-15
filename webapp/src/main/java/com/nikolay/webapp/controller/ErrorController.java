package com.nikolay.webapp.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
   * Exception handler string.
   *
   * @param ex the ex
   * @param model the model
   * @return the string
   */
  @ExceptionHandler(Exception.class)
  public final String exceptionHandler(final Exception ex,
      final Model model) {
    LOGGER.debug("exceptionHandler() msg = {}", ex.getLocalizedMessage());
    model.addAttribute("Text", ex.getMessage());
    return "_404";
  }
}