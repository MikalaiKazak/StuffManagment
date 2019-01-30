package com.nikolay.rest.errorhandler;

import com.nikolay.service.exception.OperationFailedException;
import java.util.Collections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * The type Rest error handler.
 */
@CrossOrigin
@ControllerAdvice
public class RestErrorHandler extends ResponseEntityExceptionHandler {

  /**
   * The constant LOGGER.
   */
  public static final Logger LOGGER = LogManager.getLogger();

  /**
   * Handle illegal argument exception response entity.
   *
   * @param ex the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler({IllegalArgumentException.class})
  public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex,
      WebRequest request) {
    LOGGER.error("handleIllegalArgumentException() message: " + ex.getLocalizedMessage());
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
        Collections.singletonList(ex.getMessage()), request.getDescription(true));
    return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST,
        request);
  }

  /**
   * Handle data access exception response entity.
   *
   * @param ex the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler({DataAccessException.class})
  public ResponseEntity<Object> handleDataAccessException(DataAccessException ex,
      WebRequest request) {
    LOGGER.error("handleDataAccessException() message: " + ex.getLocalizedMessage());
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
        Collections.singletonList(ex.getMessage()), request.getDescription(true));
    return handleExceptionInternal(ex, apiError, new HttpHeaders(),
        HttpStatus.BAD_REQUEST,
        request);
  }

  /**
   * Handle empty result data access exception response entity.
   *
   * @param ex the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler({EmptyResultDataAccessException.class})
  public ResponseEntity<Object> handleEmptyResultDataAccessException(
      EmptyResultDataAccessException ex, WebRequest request) {
    LOGGER.error("handleEmptyResultDataAccessException() message: " + ex.getLocalizedMessage());
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
        Collections.singletonList(ex.getMessage()), request.getDescription(true));
    return handleExceptionInternal(ex, apiError, new HttpHeaders(),
        HttpStatus.BAD_REQUEST,
        request);
  }

  /**
   * Handle illegal state exception response entity.
   *
   * @param ex the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler({IllegalStateException.class})
  public ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex,
      WebRequest request) {
    LOGGER.error("handleIllegalStateException() message: " + ex.getLocalizedMessage());
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
        Collections.singletonList(ex.getMessage()), request.getDescription(true));
    return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST,
        request);
  }

  /**
   * Handle operation failed exception response entity.
   *
   * @param ex the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler({OperationFailedException.class})
  public ResponseEntity<Object> handleOperationFailedException(OperationFailedException ex,
      WebRequest request) {
    LOGGER.error("handleOperationFailedException() message: " + ex.getLocalizedMessage());
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
        Collections.singletonList(ex.getMessage()), request.getDescription(true));
    return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST,
        request);
  }

  /**
   * Handle null pointer exception response entity.
   *
   * @param ex the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler({NullPointerException.class})
  public ResponseEntity<Object> handleNullPointerException(NullPointerException ex,
      WebRequest request) {
    LOGGER.error("handleNullPointerException() message: " + ex.getLocalizedMessage());
    ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,
        Collections.singletonList(ex.getMessage()), request.getDescription(true));
    return handleExceptionInternal(ex, apiError, new HttpHeaders(),
        HttpStatus.INTERNAL_SERVER_ERROR,
        request);
  }

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    ApiError apiError = new ApiError(HttpStatus.NOT_FOUND,
        Collections.singletonList(ex.getMessage()), request.getDescription(true));
    return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
  }

  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    ApiError apiError = new ApiError(HttpStatus.NOT_FOUND,
        Collections.singletonList(ex.getMessage()), ex.getBindingResult().toString());
    return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
  }

}
