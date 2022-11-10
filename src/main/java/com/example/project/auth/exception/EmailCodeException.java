package com.example.project.auth.exception;

public class EmailCodeException extends Throwable {
    private static final String MESSAGE = "이메일 인증 코드가 일치하지 않습니다.";

    public EmailCodeException() {
        super(MESSAGE);
    }
}
