package com.jair.tinyurl.controller;

import com.jair.tinyurl.model.Url;
import com.jair.tinyurl.model.dto.UrlDto;
import com.jair.tinyurl.service.UrlShortenerService;
import com.jair.tinyurl.service.impl.UrlShortenerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tinyurl")
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService urlShortenerService;

    @GetMapping("/all")
    public ResponseEntity<List<Url>> getAllUrl(){
        return ResponseEntity.ok(urlShortenerService.getAllUrl());
    }

    @GetMapping("/snip")
    public ResponseEntity<UrlDto> snip(){
        String urlToBeShortened = "https://www.google.com";
        String baseUrl = "https://www.tinyurl.com/";

        Url urlShortened = urlShortenerService.createUrl("a", urlToBeShortened, "a", "a", "a");

        UrlDto url = UrlDto.builder()
                .shortUrl(baseUrl + urlShortened.getHash())
                .originalUrl(urlShortened.getOriginalUrl())
                .creationDate(urlShortened.getCreationDate())
                .expirationDate(urlShortened.getExpirationDate())
                .build();

        return ResponseEntity.ok(url);
    }



}
