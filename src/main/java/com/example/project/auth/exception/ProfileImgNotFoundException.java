package com.example.project.auth.exception;

public class ProfileImgNotFoundException extends RuntimeException {
    public static final String MESSAGE = "프로필 이미지가 존재하지 않습니다.";

    public ProfileImgNotFoundException() {
        super(MESSAGE);
    }
}
