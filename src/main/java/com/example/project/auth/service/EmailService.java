package com.example.project.auth.service;

public interface EmailService {
    String sendEmailId(String email) throws Exception;

    String sendEmailPwd(String to) throws Exception;

    String sendConfirmCodeByEmail(String email) throws Exception;

    String sendReissuePassword(String email) throws Exception;

//    boolean userEmailConfirm(String code, String email);
//
//    boolean userPasswordReissue(String code, String email) throws Exception;
}