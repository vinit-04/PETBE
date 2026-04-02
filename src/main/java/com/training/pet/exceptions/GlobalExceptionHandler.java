package com.training.pet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<Map<String, String>> handleConflict(DataIntegrityViolationException ex) {
//        Map<String, String> error = new HashMap<>();
//        System.out.println(ex.getMessage());
//        ex.printStackTrace();
//        // Check if it's an email constraint violation
//        if (ex.getMessage().contains("uk6dotkott2kjsp8vw4d0m25fb7")) {
//            error.put("message", "Email already exists! Please use a different email.");
//        } else {
//            error.put("message", "Database error: Data integrity violation.");
//        }
//        System.out.println(error);
//        return new ResponseEntity<>(error, HttpStatus.CONFLICT); // 409 Conflict
//    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();

        // Agar message "Email already taken!" hai toh 400 ya 409 bhejein
        if (ex.getMessage().contains("Email already exists!")) {
            error.put("message", ex.getMessage());
            return new ResponseEntity<>(error, HttpStatus.CONFLICT);
        }

        error.put("message", "Something went wrong: " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}