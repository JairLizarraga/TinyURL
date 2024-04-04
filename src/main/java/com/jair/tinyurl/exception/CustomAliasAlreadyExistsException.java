package com.jair.tinyurl.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.CONFLICT)
public class CustomAliasAlreadyExistsException extends  RuntimeException{
    private final String customAlias;

    public CustomAliasAlreadyExistsException(String customAlias){
        this.customAlias = customAlias;
    }
}
