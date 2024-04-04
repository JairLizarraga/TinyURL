package com.jair.tinyurl.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private String errorMessage;

    @ExceptionHandler(CustomAliasAlreadyExistsException.class)
    public ResponseEntity<String> handleCustomAliasAlreadyExistsException(CustomAliasAlreadyExistsException ex){
        errorMessage = "Error: Custom alias " + ex.getCustomAlias() + " already in use. Try another one.";
        log.error(errorMessage, ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    @ExceptionHandler(UrlKeyNotFoundException.class)
    public ResponseEntity<String> handleUrlKeyNotFoundException(UrlKeyNotFoundException ex){
        errorMessage = "Error: Url short key" + ex.getUrlShortKey() + " not found in database. Nothing to delete.";
        log.error(errorMessage, ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

}
