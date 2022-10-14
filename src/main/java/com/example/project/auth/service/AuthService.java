package com.example.project.auth.service;

import com.example.project.auth.infrastructure.entity.*;
import com.example.project.auth.requestbody.CreateAuthRequest;
import com.example.project.auth.requestbody.PutAuthRequest;
import com.example.project.auth.responsebody.ReadAuthResponse;

/**
 * Create
 */
public interface AuthService {
    AuthEntity createAuth(CreateAuthRequest createAuthRequest);

    boolean checkId(ReadAuthResponse readAuthResponse);

    String putAuth(PutAuthRequest putAuthRequest);
}
