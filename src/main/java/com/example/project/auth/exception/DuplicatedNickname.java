package com.example.project.auth.exception;

import java.util.NoSuchElementException;

public class DuplicatedNickname extends NoSuchElementException {
    private static final String MESSAGE = "중복된 닉네임입니다.";

    public DuplicatedNickname() {
        super(MESSAGE);
    }
}