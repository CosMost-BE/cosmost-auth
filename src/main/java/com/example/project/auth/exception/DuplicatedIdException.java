package com.example.project.auth.exception;

import java.util.NoSuchElementException;

public class DuplicatedIdException extends NoSuchElementException {
    private static final String MESSAGE = "중복된 아이디입니다.";

    public DuplicatedIdException() {
        super(MESSAGE);
    }
}