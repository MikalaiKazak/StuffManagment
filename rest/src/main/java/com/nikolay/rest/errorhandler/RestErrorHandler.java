package com.nikolay.rest.errorhandler;

import com.nikolay.service.exception.OperationFailedException;
import java.util.Collections;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
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
   * Handle illegal argument exception response entity.
   *
   * @param ex the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler({IllegalArgumentException.class})
  public final @ResponseBody
  ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex,
      WebRequest request) {
    ApiError exceptionResponse = new ApiError(Collections.singletonList(ex.getMessage()),
        request.getDescription(true));
    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handle data integrity violation exception response entity.
   *
   * @param ex the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler(DataIntegrityViolationException.class)
  public final @ResponseBody
  ResponseEntity<ApiError> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
      WebRequest request) {
    ApiError exceptionResponse = new ApiError(
        Collections.singletonList(ex.getMessage()),
        request.getDescription(false));
    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handle data access exception response entity.
   *
   * @param ex the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler(DataAccessException.class)
  public final @ResponseBody
  ResponseEntity<Object> handleDataAccessException(DataAccessException ex, WebRequest request) {
    ApiError exceptionResponse = new ApiError(
        Collections.singletonList(ex.getMessage()),
        request.getDescription(true));
    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handle empty result data access exception response entity.
   *
   * @param ex the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler({EmptyResultDataAccessException.class})
  public final @ResponseBody
  ResponseEntity<Object> handleEmptyResultDataAccessException(
      EmptyResultDataAccessException ex, WebRequest request) {
    ApiError apiError = new ApiError(Collections.singletonList(ex.getMessage()),
        request.getDescription(true));
    return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handle illegal state exception response entity.
   *
   * @param ex the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler({IllegalStateException.class})
  public final @ResponseBody
  ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex,
      WebRequest request) {
    ApiError apiError = new ApiError(Collections.singletonList(ex.getMessage()),
        request.getDescription(true));
    return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handle operation failed exception response entity.
   *
   * @param ex the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler({OperationFailedException.class})
  public final @ResponseBody
  ResponseEntity<Object> handleOperationFailedException(OperationFailedException ex,
      WebRequest request) {
    ApiError apiError = new ApiError(Collections.singletonList(ex.getMessage()),
        request.getDescription(true));
    return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handle null pointer exception response entity.
   *
   * @param ex the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler({NullPointerException.class})
  public final @ResponseBody
  ResponseEntity<Object> handleNullPointerException(NullPointerException ex,
      WebRequest request) {
    ApiError apiError = new ApiError(Collections.singletonList(ex.getMessage()),
        request.getDescription(true));
    return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    ApiError apiError = new ApiError(Collections.singletonList(ex.getMessage()),
        request.getDescription(true));
    return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
  }

  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    ApiError errorDetails = new ApiError(
        Collections.singletonList(ex.getMessage()),
        ex.getBindingResult().toString());
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

}
