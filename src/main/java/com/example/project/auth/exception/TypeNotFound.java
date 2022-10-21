package com.example.project.auth.exception;

import java.util.NoSuchElementException;

public class TypeNotFound extends NoSuchElementException {
    private static final String MESSAGE = "입력된 타입값이 올바르지 않습니다.";

    public TypeNotFound() {
        super(MESSAGE);
    }
}
