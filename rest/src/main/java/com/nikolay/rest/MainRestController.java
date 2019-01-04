package com.nikolay.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/")
public class MainRestController {

    public static final Logger LOGGER = LogManager.getLogger();

    @RequestMapping("/")
    public ResponseEntity main() {
        LOGGER.debug("run main()");
        return new ResponseEntity(HttpStatus.OK);
    }

}
