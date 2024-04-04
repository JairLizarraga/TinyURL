package com.jair.tinyurl.model.dto;

import lombok.Getter;

@Getter
public class UrlShortenRequest {
    private String apiDevKey;
    private String urlToBeShortened;
    private String customAlias;
    private String userName;
    private String expireDate;

}