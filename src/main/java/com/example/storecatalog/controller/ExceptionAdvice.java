package com.example.storecatalog.controller;

import com.example.storecatalog.exception.NotFoundException;
import com.example.storecatalog.exception.ValidationException;
import com.example.storecatalog.view.ResponseView;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseView<String> handleNotFoundException(NotFoundException e) {
        return new ResponseView<>(e.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseView<String> handleValidationException(ValidationException e) {
        return new ResponseView<>(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }
}
