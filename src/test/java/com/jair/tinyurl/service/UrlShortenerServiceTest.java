package com.jair.tinyurl.service;

import com.jair.tinyurl.exception.CustomAliasAlreadyExistsException;
import com.jair.tinyurl.exception.InvalidCustomAliasException;
import com.jair.tinyurl.exception.UrlKeyNotFoundException;
import com.jair.tinyurl.model.Url;
import com.jair.tinyurl.repository.UrlRepository;
import com.jair.tinyurl.service.impl.UrlShortenerServiceImpl;
import jnr.constants.platform.Local;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.Assert.assertThrows;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UrlShortenerServiceTest {

    @Mock
    private UrlRepository urlShortenerRepository;

    @InjectMocks
    private UrlShortenerServiceImpl urlShortenerService;

    private String apiKey;
    private String customAlias;
    private String userName;
    private String expireDate;
    private String originalUrl;
    private Url urlMock;

    @BeforeEach
    public void setup() {
        apiKey = "asd";
        customAlias = "custom-alias";
        userName = "User name";
        expireDate = LocalDateTime.now().plusYears(1).toString();
        originalUrl = "https://www.google.com";

        urlMock = Url.builder()
                .hash("abcde")
                .originalUrl(originalUrl)
                .creationDate(LocalDateTime.now())
                .expirationDate(LocalDateTime.now())
                .build();
    }


    @Test
    public void createUrl_NoCustomAlias_success(){
        when(urlShortenerRepository.save(any())).thenReturn(urlMock);

        Url url = urlShortenerService.createUrl(apiKey, originalUrl, null, userName, expireDate);

        assertEquals(urlMock.getHash(), url.getHash());
    }


    @Test
    public void createUrl_CustomAlias_InvalidCustomAliasException(){
        String expireDate = LocalDateTime.now().plusYears(1).toString();

        customAlias = "asd";
        assertThrows(InvalidCustomAliasException.class, () ->
                urlShortenerService.createUrl(apiKey, originalUrl, customAlias, userName, expireDate));
    }

    @Test
    public void createUrl_WithCustomAlias_success(){
        Url urlMock = Url.builder().hash(customAlias).build();
        when(urlShortenerRepository.save(any())).thenReturn(urlMock);

        Url url = urlShortenerService.createUrl(apiKey, originalUrl, customAlias, userName, expireDate);

        assertEquals(customAlias, url.getHash());
    }

    @Test
    public void createUrl_WithCustomAlias_CustomAliasAlreadyExistsException(){
        when(urlShortenerRepository.findById(any())).thenReturn(Optional.ofNullable(urlMock));

        assertThrows(CustomAliasAlreadyExistsException.class, () -> urlShortenerService.createUrl(apiKey, originalUrl, customAlias, userName, expireDate));
    }

    @Test
    public void getAllUrl_success(){
        List<Url> mockUrlList = Arrays.asList(
                Url.builder().hash("hash1").build(),
                Url.builder().hash("hash2").build(),
                Url.builder().hash("hash3").build()
        );

        when(urlShortenerRepository.findAll()).thenReturn(mockUrlList);

        List<Url> allUrl = urlShortenerService.getAllUrl();
        assertEquals(mockUrlList.size(), allUrl.size());
        assertEquals(mockUrlList.getFirst().getHash(), allUrl.getFirst().getHash());
    }

    @Test
    public void deleteAllUrls_success(){
        urlShortenerService.deleteAll();
        verify(urlShortenerRepository, times(1)).deleteAll();
    }

    @Test
    public void getOriginalUrl_success(){
        when(urlShortenerRepository.findById("custom-alias")).thenReturn(Optional.ofNullable(urlMock));

        String retrievedOriginalUrl = urlShortenerService.getOriginalUrl("custom-alias");
        assertEquals(originalUrl, retrievedOriginalUrl);
    }

    @Test
    public void getOriginalUrl_urlKeyNotFoundException(){
        when(urlShortenerRepository.findById("custom-alias")).thenReturn(Optional.empty());
        assertThrows(UrlKeyNotFoundException.class, () -> urlShortenerService.getOriginalUrl("custom-alias"));
    }

    @Test
    public void deleteUrl_success(){
        when(urlShortenerRepository.findById(any())).thenReturn(Optional.ofNullable(urlMock));
        String deletionResponse = urlShortenerService.deleteUrl(apiKey, urlMock.getHash());
        assertEquals(urlMock.getHash() + " deleted successfully", deletionResponse);
    }


    @Test
    public void deleteUrl_UrlKeyNotFoundException(){
        when(urlShortenerRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(UrlKeyNotFoundException.class, () -> urlShortenerService.deleteUrl("apiDevKey", urlMock.getHash()));
    }
}
