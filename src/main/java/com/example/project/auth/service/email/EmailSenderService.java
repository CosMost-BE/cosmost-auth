package com.example.project.auth.service.email;

public interface EmailSenderService {
    String sendEmailId(String email) throws Exception;

    String sendEmailPwd(String to) throws Exception;

    String sendConfirmCodeByEmail(String email) throws Exception;

    String sendReissuePassword(String email) throws Exception;

    Boolean checkEmailDuplicate(String code, String email) throws Exception;

}