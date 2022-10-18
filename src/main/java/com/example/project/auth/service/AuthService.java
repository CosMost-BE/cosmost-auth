package com.example.project.auth.service;

import com.example.project.auth.infrastructure.entity.*;
import com.example.project.auth.requestbody.CreateAuthRequest;
import com.example.project.auth.requestbody.DeleteAuthRequest;
import com.example.project.auth.requestbody.UpdateAuthRequest;
import org.springframework.http.ResponseEntity;

/**
 * Create
 */
public interface AuthService {
    AuthEntity createAuth(CreateAuthRequest createAuthRequest);

    boolean checkId(String loginId);

    String putAuth(UpdateAuthRequest updateAuthRequest);

    ResponseEntity<String> deleteAuth(Long id, DeleteAuthRequest deleteAuthRequest);
}
