package com.example.project.auth.controller;

import com.example.project.auth.exception.TypeNotFound;
import com.example.project.auth.requestbody.UpdateAuthRequest;
import com.example.project.auth.service.AuthService;
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

    @PutMapping("")
    public ResponseEntity<?> updateAuthInfo(@RequestBody @Valid UpdateAuthRequest updateAuthRequest, HttpServletRequest request) {
        if (updateAuthRequest.getType().equals("회원정보 수정")) {
            authService.updateAuthInfo(updateAuthRequest, request);
            return ResponseEntity.ok("회원정보가 수정되었습니다.");

        } else if (updateAuthRequest.getType().equals("회원 탈퇴")) {
            authService.deleteAuthInfo(request, updateAuthRequest);
            return ResponseEntity.ok("회원탈퇴가 되었습니다.");
        }
        throw new TypeNotFound();
    }
}