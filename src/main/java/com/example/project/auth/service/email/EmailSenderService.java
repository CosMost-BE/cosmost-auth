package com.example.project.auth.service.email;

import com.example.project.auth.infrastructure.entity.AuthEntity;
import com.example.project.auth.requestbody.UpdateAuthRequest;
import com.example.project.auth.requestbody.UpdateEmailRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface EmailSenderService {
    String sendEmailId(String email) throws Exception;
    String sendEmailPwd(String to) throws Exception;

    String sendEmailNewEmail(String email, UpdateEmailRequest updateEmailRequest, HttpServletRequest request) throws Exception;

    String sendConfirmCodeByEmail(String email) throws Exception;

    String sendReissuePassword(String email) throws Exception;

    Boolean checkEmailDuplicate(String code, String email) throws Exception;

}