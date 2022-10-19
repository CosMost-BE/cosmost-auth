package com.example.project.auth.service;

import com.example.project.auth.exception.UpdateAuthFail;
import com.example.project.auth.infrastructure.entity.*;
import com.example.project.auth.requestbody.CreateAuthRequest;
import com.example.project.auth.requestbody.UpdateAuthRequest;
import com.example.project.auth.requestbody.UpdateLoginRequest;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {
    AuthEntity createAuth(CreateAuthRequest createAuthRequest);

    boolean checkId(String loginId);

    String updateLoginAuth(UpdateLoginRequest updateLoginRequest);

    // 회원정보 수정
    AuthEntity updateAuthInfo(UpdateAuthRequest updateAuthRequest, HttpServletRequest request) throws UpdateAuthFail;


}
