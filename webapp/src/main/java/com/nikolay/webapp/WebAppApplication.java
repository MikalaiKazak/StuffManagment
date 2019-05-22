package com.nikolay.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations = "classpath:WEB-INF/root-config.xml")
public class WebAppApplication {

  public static void main(String[] args) {
    SpringApplication.run(WebAppApplication.class, args);
  }
}
