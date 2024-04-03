package com.jair.tinyurl.service;

import com.jair.tinyurl.repository.UrlRepository;
import com.jair.tinyurl.service.impl.UrlShortenerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ShortenerServiceTest {

    @Mock
    private UrlRepository urlShortenerRepository;

    @InjectMocks
    private UrlShortenerServiceImpl urlShortenerServiceImpl;

    private UrlShortener urlShortenerMock;

    @BeforeEach
    public void setup(){
        urlShortenerMock = UrlShortener.builder()
                .build();
    }

    @Test
    public void createUrl_success(){
        when(urlShortenerRepository.save(any())).thenReturn(urlShortenerMock);

        UrlShortener urlShortened = urlShortenerServiceImpl.createUrlShortened("https://www.google.com");
        assertEquals("short", urlShortened.getShortenedUrl());

    }
}
