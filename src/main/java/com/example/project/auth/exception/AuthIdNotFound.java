package com.example.project.auth.exception;

public class AuthIdNotFound extends IllegalArgumentException {

    private final static String MESSAGE = "유저아이디를 찾을 수 없습니다.";

    public AuthIdNotFound() {
        super(MESSAGE);
    }
}
