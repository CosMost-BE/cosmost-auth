package com.example.project.auth.exception;

public class ReadAuthFail extends RuntimeException{

    public static final String MESSAGE = "회원정보 조회에 실패했습니다.";

    public ReadAuthFail() {
        super(MESSAGE);
    }
}
