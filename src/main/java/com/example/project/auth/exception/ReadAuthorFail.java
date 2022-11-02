package com.example.project.auth.exception;

public class ReadAuthorFail extends RuntimeException{

    public static final String MESSAGE = "작성자 정보 조회에 실패했습니다.";


    public ReadAuthorFail() {
        super(MESSAGE);
    }
}
