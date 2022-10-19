package com.example.project.auth.exception;

import java.util.NoSuchElementException;

public class UpdateAuthFail extends NoSuchElementException {

    private static final String MESSAGE = "회원정보 변경에 실패했습니다.";

    public UpdateAuthFail() {
        super(MESSAGE);
    }
}
