package com.example.project.auth.exception;

import java.util.NoSuchElementException;

public class UpdatePasswordFail extends NoSuchElementException {
    private static final String MESSAGE = "비밀번호 변경에 실패하였습니다.";

    public UpdatePasswordFail() {
        super(MESSAGE);
    }
}
