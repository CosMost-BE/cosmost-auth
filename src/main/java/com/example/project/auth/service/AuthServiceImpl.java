package com.example.project.auth.service;

import com.example.project.auth.infrastructure.entity.AuthEntity;
import com.example.project.auth.infrastructure.repository.AuthEntityRepository;
import com.example.project.auth.requestbody.CreateAuthRequest;
import com.example.project.auth.requestbody.PutAuthRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String createAuth(CreateAuthRequest createAuthRequest) {
        AuthEntity auth = dtoEntity(createAuthRequest);
        authEntityRepository.save(auth);
        return String.valueOf(auth.getId());
    }

    @Override
    public String putAuth(PutAuthRequest putAuthRequest) {
        AuthEntity auth = dtoEntity(putAuthRequest);
        authEntityRepository.save(auth);
        return String.valueOf(auth.getId());
    }
}