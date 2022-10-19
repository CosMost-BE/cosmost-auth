package com.example.project.auth.service;

import com.example.project.auth.exception.UpdateAuthFail;
import com.example.project.auth.infrastructure.entity.*;
import com.example.project.auth.requestbody.CreateAuthRequest;
import com.example.project.auth.requestbody.UpdateAuthRequest;
import com.example.project.auth.requestbody.UpdateLoginRequest;

/**
 * Create
 */
public interface AuthService {
    AuthEntity createAuth(CreateAuthRequest createAuthRequest);

    AuthEntity updateAuth(UpdateAuthRequest updateAuthRequest, Long id);

    boolean checkId(String loginId);

    String updateLoginaAuth(UpdateLoginRequest putAuthRequest);

}
