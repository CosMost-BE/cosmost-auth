package com.example.project.auth.service;

import com.example.project.auth.infrastructure.entity.*;
import com.example.project.auth.requestbody.CreateAuthRequest;
import com.example.project.auth.requestbody.UpdateAuthRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Create
 */
public interface AuthService {
    AuthEntity createAuth(CreateAuthRequest createAuthRequest);

    boolean checkId(String loginId);

    String putAuth(UpdateAuthRequest updateAuthRequest);

    Optional<AuthEntity> deleteAuth(HttpServletRequest request);
}
