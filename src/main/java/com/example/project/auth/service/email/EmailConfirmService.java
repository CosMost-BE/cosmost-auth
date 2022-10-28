package com.example.project.auth.service.email;

public interface EmailConfirmService {

    boolean userEmailConfirm(String code, String email);

    boolean userIdReissue(String code, String email) throws Exception;

    boolean userPasswordReissue(String code, String email) throws Exception;
}
