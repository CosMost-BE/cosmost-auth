package com.example.project.auth.controller;

import com.example.project.auth.requestbody.UpdateAuthRequest;
import com.example.project.auth.service.AuthService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.project.auth.configuration.util.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RequestMapping(value = "/v1/auths")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthInfoController {
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthInfoController(AuthService authService, JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @ApiResponses({
            @ApiResponse(code=201, message = "회원정보 수정 완료"),
            @ApiResponse(code=401, message = "회원정보 항목이 수정되지 않았습니다. 다시 확인하세요"),
            @ApiResponse(code=403, message = "권한이 존재하지 않습니다."),
            @ApiResponse(code=404, message = "데이터가 없습니다. 요청한 페이지를 찾을 수 없습니다.")
    })
    @ApiOperation(value = "회원정보 수정할 때 쓰는 메소드")
    @ApiImplicitParam(name = "authInfo", value = "회원정보 수정", dataType = "AuthInfoVoReq")
    @PutMapping("")
    public ResponseEntity<?> updateAuthInfo(@RequestBody @Valid UpdateAuthRequest updateAuthRequest, HttpServletRequest request) {
        String auth = String.valueOf(authService.updateAuthInfo(updateAuthRequest, request));

        if(auth != null) {
            return ResponseEntity.status(200).body(auth);
        } else {
            return ResponseEntity.status(400).body("error page");
        }
    }
}