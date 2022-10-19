package com.example.project.auth.service;

import com.example.project.auth.exception.DuplicatedIdException;
import com.example.project.auth.exception.DuplicatedNickname;
import com.example.project.auth.infrastructure.entity.*;
import com.example.project.auth.requestbody.CreateAuthRequest;
import com.example.project.auth.requestbody.PutAuthRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * Create
 */
public interface AuthService {
    AuthEntity createAuth(CreateAuthRequest createAuthRequest);

    Boolean checkId(HttpServletRequest request) throws DuplicatedIdException;

    Boolean checkNickname(HttpServletRequest request) throws DuplicatedNickname;

    String putAuth(PutAuthRequest putAuthRequest);
}
