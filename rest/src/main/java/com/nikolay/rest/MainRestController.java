package com.nikolay.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Main rest controller.
 */
@RestController
@RequestMapping("/")
public class MainRestController {

  /**
   * The constant LOGGER.
   */
  public static final Logger LOGGER = LogManager.getLogger();

  /**
   * Main response entity.
   *
   * @return the response entity
   */
  @RequestMapping("/")
  public ResponseEntity<Void> main() {
    LOGGER.debug("run main()");
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
