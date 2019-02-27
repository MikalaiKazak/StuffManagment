package com.nikolay.client.handler;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResponseErrorHandler;

import com.nikolay.client.exception.ServerDataAccessException;

/**
 * The type Custom response error handler.
 */
public class CustomResponseErrorHandler implements ResponseErrorHandler {

  private static final Logger LOGGER = LogManager.getLogger();

  private ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();

  @Override
  public boolean hasError(ClientHttpResponse response) throws IOException {
    return errorHandler.hasError(response);
  }

  @Override
  public void handleError(ClientHttpResponse response) throws IOException {
    LOGGER.debug("CustomResponseErrorHandler - handleError()");
    try {
      errorHandler.handleError(response);
    } catch (HttpStatusCodeException e) {
      throw new ServerDataAccessException(e.getResponseBodyAsString());
    }
  }
}
