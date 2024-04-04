package com.jair.tinyurl.service;

import com.jair.tinyurl.model.Url;

import java.util.List;

public interface UrlShortenerService {

    List<Url> getAllUrl();
    void deleteAll();

    Url createUrl(String apiDevKey, String originalUrl, String customAlias, String userName, String expireDate);

    String deleteUrl(String apiDevKey, String urlKey);

}
