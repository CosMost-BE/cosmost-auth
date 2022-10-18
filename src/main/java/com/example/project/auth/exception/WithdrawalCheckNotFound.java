package com.example.project.auth.exception;

public class WithdrawalCheckNotFound extends RuntimeException{

    public static final String MESSAGE = "회원 탈퇴 여부를 체크해주세요.";

    public WithdrawalCheckNotFound() {
        super(MESSAGE);
    }
}
