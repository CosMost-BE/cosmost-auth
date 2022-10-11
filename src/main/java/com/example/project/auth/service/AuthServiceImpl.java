package com.example.project.auth.service;

import com.example.project.auth.infrastructure.entity.AuthEntity;
import com.example.project.auth.infrastructure.repository.AuthEntityRepository;
import com.example.project.auth.requestbody.CreateAuthRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    private final AuthEntityRepository authEntityRepository;

    @Autowired
    public AuthServiceImpl(AuthEntityRepository authEntityRepository) {
        this.authEntityRepository = authEntityRepository;
    }

    @Override
    public Long createAuth(CreateAuthRequest createAuthRequest) {
        AuthEntity auth = dtoEntity(createAuthRequest);
        authEntityRepository.save(auth);
        return auth.getId();
    }
}