package com.jair.tinyurl.repository;

import com.jair.tinyurl.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
