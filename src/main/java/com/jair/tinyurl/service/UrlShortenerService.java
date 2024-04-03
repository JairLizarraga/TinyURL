package com.jair.tinyurl.service;

import com.jair.tinyurl.model.Url;
import com.jair.tinyurl.model.dto.UrlDto;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface UrlShortenerService {

    public List<Url> getAllUrl();

    public Url createUrl(String apiDevKey, String originalUrl, String customAlias, String userName, String expireDate);

    public String deleteUrl(String apiDevKey, String urlKey);

}
