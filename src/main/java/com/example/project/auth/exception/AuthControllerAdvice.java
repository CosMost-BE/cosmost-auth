package com.example.project.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthControllerAdvice {

    // 중복 아이디 예외처리
    @ExceptionHandler(DuplicatedIdException.class)
    public ResponseEntity<String> DuplicatedIdException(DuplicatedIdException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(DuplicatedNickname.class)
    public ResponseEntity<String> DuplicatedNameException(DuplicatedNickname exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(EmailSendException.class)
    public ResponseEntity<String> EmailSendException(EmailSendException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(EmailCodeException.class)
    public ResponseEntity<String> EmailCodeException(WithdrawalCheckNotFound exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(ReadAuthFail.class)
    public ResponseEntity<String> ReadAuthFailException(ReadAuthFail exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(TypeNotFound.class)
    public ResponseEntity<String> TypeNotFoundException(TypeNotFound exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }


    // 회원정보 수정 예외처리
    @ExceptionHandler(UpdateAuthFail.class)
    public ResponseEntity<String> UpdateAuthFailException(UpdateAuthFail exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(UpdatePasswordFail.class)
    public ResponseEntity<String> UpdatePasswordFailException(UpdateAuthFail exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }


    @ExceptionHandler(WithdrawalCheckNotFound.class)
    public ResponseEntity<String> WithdrawalCheckNotFoundException(WithdrawalCheckNotFound exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String processValidationError(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        StringBuilder builder = new StringBuilder();
        FieldError fieldError = bindingResult.getFieldError();
        builder.append(fieldError.getDefaultMessage());

        return builder.toString();
    }
}
