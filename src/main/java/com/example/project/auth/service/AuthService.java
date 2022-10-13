package com.example.project.auth.service;

import com.example.project.auth.infrastructure.entity.*;
import com.example.project.auth.requestbody.CreateAuthRequest;
import com.example.project.auth.requestbody.PutAuthRequest;

import java.util.Optional;

/**
 * Create
 */
public interface AuthService {
    AuthEntity createAuth(CreateAuthRequest createAuthRequest);

    String checkUserId(String loginId);

    String putAuth(PutAuthRequest putAuthRequest);
}
