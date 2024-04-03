package com.jair.tinyurl.service.impl;

import com.jair.tinyurl.model.Url;
import com.jair.tinyurl.repository.UrlRepository;
import com.jair.tinyurl.service.UrlShortenerService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

    private final UrlRepository urlRepository;

    public UrlShortenerServiceImpl(UrlRepository urlRepository){
        this.urlRepository = urlRepository;
    }


    public List<Url> getAllUrl(){
        Iterable<Url> all = urlRepository.findAll();
        List<Url> urlList = new ArrayList<>();
        all.forEach(urlList::add);
        return urlList;
    }

    public void deleteAll(){
        urlRepository.deleteAll();
    }


    private String generateShortUrl(String originalUrl) {
        String hashCode;
        Optional<Url> urlInDatabase;
        int increaseSequenceForUniqueness = 0;
        String encodedUrl;

        do {
            hashCode = Integer.toString((increaseSequenceForUniqueness + originalUrl).hashCode());
            encodedUrl = Base64.getEncoder().encodeToString(hashCode.getBytes()).substring(0, 6);
            urlInDatabase = urlRepository.findByHash(encodedUrl);
            increaseSequenceForUniqueness++;
        } while (urlInDatabase.isPresent());


        return encodedUrl;
    }

    @Override
    public Url createUrl(String apiDevKey, String originalUrl, String customAlias, String userName, String expireDate) {
        String uniqueHashCodeFromUrl = generateShortUrl(originalUrl);

        Url urlShortened = Url.builder()
            .hash(uniqueHashCodeFromUrl)
            .originalUrl(originalUrl)
            .creationDate(LocalDateTime.now())
            .expirationDate(LocalDateTime.now().plusYears(1))
            .build();

        urlRepository.save(urlShortened);
        return urlShortened;
    }

    @Override
    public String deleteUrl(String apiDevKey, String urlKey) {
        return null;
    }
    
}
