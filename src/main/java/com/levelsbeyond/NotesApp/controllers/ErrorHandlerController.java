package com.levelsbeyond.NotesApp.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ErrorHandlerController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public ResponseEntity error(HttpServletRequest request, HttpServletResponse response) {

        return ResponseEntity.status(HttpStatus.OK)
                .body("Oops! Something went Wrong!! Please check the API documentation.");
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }



}
