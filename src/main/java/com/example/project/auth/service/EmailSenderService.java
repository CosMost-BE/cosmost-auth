package com.example.project.auth.service;

public interface EmailSenderService {
    String sendEmailId(String email) throws Exception;

    String sendEmailPwd(String to) throws Exception;

    String sendConfirmCodeByEmail(String email) throws Exception;

    String sendReissuePassword(String email) throws Exception;

//    AuthEntity checkEmailDuplicate(String code, String email) throws Exception;

}