package com.example.project.auth.controller;

import com.example.project.auth.requestbody.CreateAuthRequest;
import com.example.project.auth.requestbody.PutAuthRequest;
import com.example.project.auth.service.AuthService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/v1")
@RestController
public class AuthController {
    private final AuthService authService;
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 코스리뷰에 등록하는 api
    @ApiResponses({
            @ApiResponse(code=201, message = "리뷰 등록완료 !!!!!!"),
            @ApiResponse(code=401, message = "리뷰가 등록되지 않았습니다, 다시 확인하세요"),
            @ApiResponse(code=403, message = "권한이 존재하지 않습니다."),
            @ApiResponse(code=404, message = "리뷰를 찾을 수 없습니다.")
    })


    @ApiOperation(value = "회원가입을 할 때 쓰는 메소드")
    @ApiImplicitParam(name = "auth", value = "회원가입", dataType = "AuthVoReq")
    @PostMapping("/auths")
    public ResponseEntity<String> createAuth(@Valid @RequestBody CreateAuthRequest createAuthRequest) {
        authService.createAuth(createAuthRequest);
        return ResponseEntity.ok().body("회원가입이 되었습니다.");
    }


    @ApiOperation(value = "로그인 할 때 쓰는 메소드")
    @ApiImplicitParam(name = "login", value = "로그인", dataType = "LoginVoReq")
    @PutMapping("/login")
    public ResponseEntity<String> putAuth(@Valid @RequestBody PutAuthRequest putAuthRequest) {
        authService.putAuth(putAuthRequest);
        return ResponseEntity.ok().body("로그인이 되었습니다.");
    }
}