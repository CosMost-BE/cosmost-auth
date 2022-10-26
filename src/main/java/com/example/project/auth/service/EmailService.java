package com.example.project.auth.service;

import com.example.project.auth.infrastructure.entity.AuthEntity;

public interface EmailService {
    String sendEmailId(String email) throws Exception;

    String sendEmailPwd(String to) throws Exception;

    String sendConfirmCodeByEmail(String email) throws Exception;

    String sendReissuePassword(String email) throws Exception;

    boolean userEmailConfirm(String code, String email);

    boolean userIdReissue(String code, String email) throws Exception;

    boolean userPasswordReissue(String code, String email) throws Exception;

    AuthEntity checkEmailDuplicate(String code, String email) throws Exception;

}