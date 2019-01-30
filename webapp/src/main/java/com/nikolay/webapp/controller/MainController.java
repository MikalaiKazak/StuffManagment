package com.nikolay.webapp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The type Main controller.
 */
@Controller
@RequestMapping("/")
public class MainController {

  /**
   * The constant LOGGER.
   */
  public static final Logger LOGGER = LogManager.getLogger();

  /**
   * Main page string.
   *
   * @return the string
   */
  @GetMapping
  public String mainPage() {
    LOGGER.debug("mainPage()");
    return "index";
  }

}
