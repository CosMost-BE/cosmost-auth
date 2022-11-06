package com.example.project.auth.service.email;

import com.example.project.auth.exception.EmailCodeException;
import com.example.project.auth.infrastructure.entity.AuthEntity;
import com.example.project.auth.requestbody.UpdateEmailRequest;

import javax.servlet.http.HttpServletRequest;

public interface EmailConfirmService {

    boolean userEmailConfirm(String code, String email);

    String userIdReissue(String code, String email) throws Exception, EmailCodeException;

    Long userPasswordReissue(String code, String email) throws EmailCodeException;

    // 비밀번호 찾기 시 새 비밀번호 입력
    AuthEntity userNewpasswordReissue(Long id, String newpwd);

    // 이메일 변경 시
    AuthEntity userNewEmailReissue(String code, String email, HttpServletRequest request, UpdateEmailRequest updateEmailRequest);
}
