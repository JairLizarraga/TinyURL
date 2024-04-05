package com.jair.tinyurl.service.impl;

import com.jair.tinyurl.exception.CustomAliasAlreadyExistsException;
import com.jair.tinyurl.exception.UrlKeyNotFoundException;
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

    @Override
    public String deleteUrl(String apiDevKey, String urlKey) {
        Optional<Url> byHash = urlRepository.findById(urlKey);
        if(byHash.isEmpty())
            throw new UrlKeyNotFoundException(urlKey);
        urlRepository.deleteById(urlKey);
        return urlKey + " deleted successfully";
    }

    @Override
    public String getOriginalUrl(String urlKey) {
        Optional<Url> byId = urlRepository.findById(urlKey);
        if(byId.isEmpty())
            throw new UrlKeyNotFoundException(urlKey);

        return byId.get().getOriginalUrl();
    }


    @Override
    public Url createUrl(String apiDevKey, String originalUrl, String customAlias, String userName, String expireDate) {
        String uniqueHashCodeFromUrl = getUniqueHashCode(originalUrl, customAlias);

        Url urlShortened = Url.builder()
                .hash(uniqueHashCodeFromUrl)
                .originalUrl(originalUrl)
                .creationDate(LocalDateTime.now())
                .expirationDate(LocalDateTime.now().plusYears(1))
                .build();

        urlRepository.save(urlShortened);
        return urlShortened;
    }

    public String getUniqueHashCode(String originalUrl, String customAlias){
        if(customAlias != null){
            if(isAliasAvailable(customAlias))
                return customAlias;
            throw new CustomAliasAlreadyExistsException(customAlias);
        } else {
            return generateShortUrl(originalUrl);
        }
    }

    private String generateShortUrl(String originalUrl) {
        String hashCode;
        int increaseSequenceForUniqueness = 0;
        String encodedUrl;

        do {
            hashCode = Integer.toString((increaseSequenceForUniqueness + originalUrl).hashCode());
            encodedUrl = Base64.getEncoder().encodeToString(hashCode.getBytes()).substring(0, 6);
            increaseSequenceForUniqueness++;
        } while (urlRepository.findById(encodedUrl).isPresent());

        return encodedUrl;
    }

    public boolean isAliasAvailable(String customAlias){
        Optional<Url> findAliasInDatabase = urlRepository.findById(customAlias);
        return findAliasInDatabase.isEmpty();
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

    
}
