package com.jair.tinyurl.repository;

import com.jair.tinyurl.model.Url;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UrlRepository extends CrudRepository<Url, String> {
    Optional<Url> findByHash(String hash);
}
