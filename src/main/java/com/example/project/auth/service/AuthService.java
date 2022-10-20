package com.example.project.auth.service;

import com.example.project.auth.exception.WithdrawalCheckNotFound;
import com.example.project.auth.infrastructure.entity.*;
import com.example.project.auth.requestbody.CreateAuthRequest;
import com.example.project.auth.requestbody.DeleteAuthRequest;
import com.example.project.auth.requestbody.UpdateAuthRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Create
 */
public interface AuthService {
    AuthEntity createAuth(CreateAuthRequest createAuthRequest);

    boolean checkId(String loginId);

    String putAuth(UpdateAuthRequest updateAuthRequest); // 로그인

    AuthEntity putUserAuth(HttpServletRequest request, DeleteAuthRequest deleteAuthRequest) throws WithdrawalCheckNotFound;

}
