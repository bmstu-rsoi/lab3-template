package com.example.gatewayservice.controller;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler({FeignException.class})
    public ResponseEntity<String> handleResourceNotFoundException(FeignException feignException) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("message", feignException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{"+feignException.getMessage().split("\\{|\\}")[1] + "}");
    }
}
