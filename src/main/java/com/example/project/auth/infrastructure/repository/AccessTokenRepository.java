package com.example.project.auth.infrastructure.repository;

import com.example.project.auth.infrastructure.entity.AccessToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccessTokenRepository extends CrudRepository<AccessToken, String> {
    public Optional<AccessToken> findByToken(String token);
}
