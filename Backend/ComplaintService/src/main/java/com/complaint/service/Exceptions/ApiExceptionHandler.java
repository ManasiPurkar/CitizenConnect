package com.complaint.service.Exceptions;

import com.user.service.DTOs.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {APIRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(APIRequestException e) {
        String customMessage = e.getMessage();
        String originalMessage = e.getDetails();

        if (originalMessage != null) {
            ApiException apiException = new ApiException(customMessage, originalMessage, HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
        } else {
            ApiException apiException = new ApiException(customMessage, null, HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
        }
    }
    }
