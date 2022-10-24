package com.example.project.auth.service;

import com.example.project.auth.exception.*;
import com.example.project.auth.infrastructure.entity.*;
import com.example.project.auth.model.Auth;
import com.example.project.auth.requestbody.CreateAuthRequest;
import com.example.project.auth.requestbody.UpdateAuthRequest;
import com.example.project.auth.requestbody.UpdateLoginRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * Create
 */
public interface AuthService {

    // 회원가입
    AuthEntity createAuth(CreateAuthRequest createAuthRequest);

    // 중복 아이디 확인
    boolean checkId(HttpServletRequest request) throws DuplicatedIdException;

    // 중복 닉네임 확인
    boolean checkNickname(HttpServletRequest request) throws DuplicatedNickname;

    // 로그인
    String updateLoginAuth(UpdateLoginRequest updateLoginRequest);

    // 회원 탈퇴
    void deleteAuthInfo(HttpServletRequest request, UpdateAuthRequest updateAuthRequest) throws WithdrawalCheckNotFound;

    // 회원정보 수정
    void updateAuthInfo(UpdateAuthRequest updateAuthRequest, HttpServletRequest request) throws UpdateAuthFail;

    // 회원정보 조회
    Auth readAuth(HttpServletRequest request) throws ReadAuthFail;

    Object readAuthor(HttpServletRequest request) throws  ReadAuthorFail;
}
