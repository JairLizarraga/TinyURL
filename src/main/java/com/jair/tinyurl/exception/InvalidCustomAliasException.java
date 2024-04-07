package com.jair.tinyurl.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCustomAliasException extends  RuntimeException{

    private final String customAlias;
    private final String message;

    public InvalidCustomAliasException(String customAlias, String message){
        this.customAlias = customAlias;
        this.message = message;
    }

}
