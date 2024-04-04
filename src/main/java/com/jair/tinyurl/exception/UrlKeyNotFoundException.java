package com.jair.tinyurl.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UrlKeyNotFoundException extends RuntimeException{
    private final String urlShortKey;

    public UrlKeyNotFoundException(String urlShortKey){
        this.urlShortKey = urlShortKey;
    }
}
