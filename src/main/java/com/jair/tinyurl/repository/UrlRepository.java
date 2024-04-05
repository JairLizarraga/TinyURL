package com.jair.tinyurl.repository;

import com.jair.tinyurl.model.Url;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UrlRepository extends CassandraRepository<Url, String> {

}
