package com.example.project.auth.service.email;

import com.example.project.auth.requestbody.UpdateEmailRequest;
import javax.servlet.http.HttpServletRequest;

public interface EmailSenderService {
    String sendEmailId(String email) throws Exception;
    String sendEmailPwd(String to) throws Exception;

    String sendEmailNewEmail(String email, UpdateEmailRequest updateEmailRequest, HttpServletRequest request) throws Exception;

    String sendConfirmCodeByEmail(String email, HttpServletRequest request) throws Exception;

    String sendReissuePassword(String email) throws Exception;

}