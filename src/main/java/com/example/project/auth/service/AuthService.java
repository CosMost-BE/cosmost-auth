package com.example.project.auth.service;

import com.example.project.auth.infrastructure.entity.*;
import com.example.project.auth.requestbody.CreateAuthRequest;
import com.example.project.auth.requestbody.PutAuthRequest;

/**
 * Create
 */
public interface AuthService {
    String createAuth(CreateAuthRequest createAuthRequest);
    String putAuth(PutAuthRequest putAuthRequest);
}
