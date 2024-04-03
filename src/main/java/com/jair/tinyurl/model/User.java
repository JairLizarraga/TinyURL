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
public class User {

    @PrimaryKey
    private Integer userId;

    private String name;
    private String email;
    private LocalDateTime creationDate;
    private LocalDateTime lastLogin;

}
