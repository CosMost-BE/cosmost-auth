package com.example.project.auth.controller;

import com.example.project.auth.infrastructure.entity.AuthEntity;
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
import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.util.Optional;

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

            Optional<AuthEntity> authUpdate = Optional.ofNullable(authService.updateAuthInfo(updateAuthRequest, request));

            if (authUpdate.isPresent()) {
                return ResponseEntity.status(200).body(authUpdate);
            } else {
                return ResponseEntity.status(400).body("회원정보 수정 실패");
            }

        } else if (updateAuthRequest.getType().equals("회원 탈퇴")) {

            Optional<AuthEntity> authDelete = Optional.ofNullable(authService.deleteAuthInfo(request, updateAuthRequest));

            if (authDelete.isPresent()) {
                return ResponseEntity.status(200).body("회원탈퇴 성공");
            } else {
                return ResponseEntity.status(400).body("회원탈퇴 실패");
            }

        }
        return null;
    }
}