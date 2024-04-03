package com.jair.tinyurl.model;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
public class Url {

    @PrimaryKey
    private String hash;

    private String originalUrl;
    private LocalDateTime creationDate;
    private LocalDateTime expirationDate;

}
