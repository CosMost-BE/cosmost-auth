package com.example.project.auth.service.email;

import com.example.project.auth.exception.EmailCodeException;
import com.example.project.auth.infrastructure.entity.AuthEntity;

public interface EmailConfirmService {

    boolean userEmailConfirm(String code, String email);

    String userIdReissue(String code, String email) throws Exception, EmailCodeException;

    Long userPasswordReissue(String code, String email) throws EmailCodeException;

    AuthEntity userNewpasswordReissue(Long id, String newpwd);
}
