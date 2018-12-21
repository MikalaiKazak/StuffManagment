package com.nikolay.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainRestController {

    @RequestMapping("/")
    public ResponseEntity main() {
        return new ResponseEntity(HttpStatus.OK);
    }

}
