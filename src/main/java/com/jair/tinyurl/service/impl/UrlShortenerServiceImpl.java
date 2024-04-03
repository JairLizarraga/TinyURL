package com.jair.tinyurl.service.impl;

import com.jair.tinyurl.model.Url;
import com.jair.tinyurl.model.dto.UrlDto;
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

    public String getUniqueHashCodeFromUrl(String originalUrl){
        String hashCode;
        Optional<Url> urlInDatabase;
        Integer increaseSequenceForUniqueness = 0;

        do{
            hashCode = Integer.toString(originalUrl.hashCode());
            urlInDatabase = urlRepository.findByHash(hashCode);
            originalUrl += Integer.toString(increaseSequenceForUniqueness++);
        } while (urlInDatabase.isPresent());

        return hashCode;
    }

    private String getShortCode(String hashCode){
        String encodedUrl = Base64.getEncoder().encodeToString(hashCode.getBytes());
        return encodedUrl.substring(0, 6);
    }

    public List<Url> getAllUrl(){
        Iterable<Url> all = urlRepository.findAll();
        List<Url> urlList = new ArrayList<>();
        all.forEach(urlList::add);
        return urlList;
    }
    
    @Override
    public Url createUrl(String apiDevKey, String originalUrl, String customAlias, String userName, String expireDate) {
        String uniqueHashCodeFromUrl = getUniqueHashCodeFromUrl(originalUrl);
        String shortUrl = getShortCode(uniqueHashCodeFromUrl);

        Url urlShortened = Url.builder()
            .hash(shortUrl)
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
