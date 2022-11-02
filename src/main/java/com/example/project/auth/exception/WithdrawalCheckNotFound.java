package com.example.project.auth.exception;

public class WithdrawalCheckNotFound extends RuntimeException{

    public static final String MESSAGE = "회원탈퇴 여부 혹은 비밀번호 입력을 확인해주세요.";

    public WithdrawalCheckNotFound() {
        super(MESSAGE);
    }
}
