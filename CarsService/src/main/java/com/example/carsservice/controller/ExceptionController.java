package com.example.carsservice.controller;

import com.google.gson.Gson;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class ExceptionController {
    private final Gson gson = new Gson();

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<String> handleResourceNotFoundException(ConstraintViolationException constraintViolationException) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("message", constraintViolationException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gson.toJson(hashMap));
    }
}
