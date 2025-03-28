package com.ucad.m2SIR.SenBook.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

    @RequestMapping("/**")
    public ResponseEntity<String> handleDefaultRequest() {
        return new ResponseEntity<>("Failed : Le point que vous essayer d'atteindre n'existe pas", HttpStatus.NOT_FOUND);
    }
}
