package com.example.project.auth.exception;
import java.util.NoSuchElementException;

public class DuplicatedEmailException extends NoSuchElementException {
    private static final String MESSAGE = "중복된 이메일입니다.";
    public DuplicatedEmailException() {
        super(MESSAGE);
    }
}