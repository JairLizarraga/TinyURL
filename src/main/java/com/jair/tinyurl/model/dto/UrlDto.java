package com.jair.tinyurl.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UrlDto {
    private String shortUrl;
    private String originalUrl;
    private LocalDateTime creationDate;
    private LocalDateTime expirationDate;
}
