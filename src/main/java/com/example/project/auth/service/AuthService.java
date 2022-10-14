package com.example.project.auth.service;

import com.example.project.auth.infrastructure.entity.*;
import com.example.project.auth.requestbody.CreateAuthRequest;
import com.example.project.auth.requestbody.PutAuthRequest;

/**
 * Create
 */
public interface AuthService {
    AuthEntity createAuth(CreateAuthRequest createAuthRequest);

    boolean checkId(String loginId);

    String putAuth(PutAuthRequest putAuthRequest);
}
