package com.jair.tinyurl.controller;

import com.jair.tinyurl.model.Url;
import com.jair.tinyurl.model.dto.UrlDto;
import com.jair.tinyurl.model.dto.UrlShortenRequest;
import com.jair.tinyurl.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/deleteall")
    public ResponseEntity<String> deleteAll(){
        urlShortenerService.deleteAll();
        return ResponseEntity.ok("Deleted all urls");
    }

    @PostMapping("/snip")
    public ResponseEntity<UrlDto> snip(@RequestBody UrlShortenRequest request){
        String apiDevKey = request.getApiDevKey();
        String urlToBeShortened = request.getUrlToBeShortened();
        String customAlias = request.getCustomAlias();
        String userName = request.getUserName();
        String expireDate = request.getExpireDate();


        String baseUrl = "https://www.tinyurl.com/";

        Url urlShortened = urlShortenerService.createUrl(apiDevKey, urlToBeShortened, customAlias, userName, expireDate);

        UrlDto urlResponse = UrlDto.builder()
                .shortUrl(baseUrl + urlShortened.getHash())
                .originalUrl(urlShortened.getOriginalUrl())
                .creationDate(urlShortened.getCreationDate())
                .expirationDate(urlShortened.getExpirationDate())
                .build();

        return ResponseEntity.ok(urlResponse);
    }

    @DeleteMapping("/delete/{urlKey}")
    public ResponseEntity<String> deleteShortUrl(@PathVariable String urlKey){
        String asd = urlShortenerService.deleteUrl("asd", urlKey);
        return ResponseEntity.ok(asd);
    }

    @GetMapping("/geturl/{customAlias}")
    public ResponseEntity<String> getOriginalUrl(@PathVariable String urlKey){
        String originalUrl = urlShortenerService.getOriginalUrl(urlKey);
        return ResponseEntity.ok(originalUrl);
    }


}
