package com.example.project.auth.service;

import com.example.project.auth.exception.UpdateAuthFail;
import com.example.project.auth.exception.DuplicatedIdException;
import com.example.project.auth.exception.DuplicatedNickname;
import com.example.project.auth.exception.WithdrawalCheckNotFound;
import com.example.project.auth.infrastructure.entity.*;
import com.example.project.auth.requestbody.CreateAuthRequest;
import com.example.project.auth.requestbody.UpdateAuthRequest;
import com.example.project.auth.requestbody.UpdateLoginRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * Create
 */
public interface AuthService {
    AuthEntity createAuth(CreateAuthRequest createAuthRequest);

    Boolean checkId(HttpServletRequest request) throws DuplicatedIdException;

    Boolean checkNickname(HttpServletRequest request) throws DuplicatedNickname;

    String updateLoginAuth(UpdateLoginRequest updateLoginRequest);

    // 회원 탈퇴
    AuthEntity deleteAuthInfo(HttpServletRequest request, UpdateAuthRequest updateAuthRequest) throws WithdrawalCheckNotFound;
    // 회원정보 수정
    AuthEntity updateAuthInfo(UpdateAuthRequest updateAuthRequest, HttpServletRequest request) throws UpdateAuthFail;

}
