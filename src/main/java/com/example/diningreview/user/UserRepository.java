package com.example.diningreview.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsByDisplayName(String displayName);
    Optional<User> findByDisplayName(String displayName);
}
