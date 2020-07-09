package com.example.demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalDefaultExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> notValidException(MethodArgumentNotValidException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFoundException(NotFoundException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }

}
