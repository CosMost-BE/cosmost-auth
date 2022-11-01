package com.example.project.auth.exception;

public class EmailSendException extends Throwable {
    private static final String MESSAGE = "이메일 요청이 실패하였습니다.";
    public EmailSendException() {
        super(MESSAGE);
    }
}
