package com.example.project.auth.controller;

import com.example.project.auth.configuration.util.JwtTokenProvider;
import com.example.project.auth.infrastructure.entity.AuthEntity;
import com.example.project.auth.requestbody.CreateOAuthRequest;
import com.example.project.auth.requestbody.UpdateLoginRequest;
import com.example.project.auth.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.util.HashMap;

@Slf4j
@RequestMapping("/v1/signin")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {
    private final AuthService authService;

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public LoginController(AuthService authService, JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PutMapping("")
    public ResponseEntity<String> updateLoginAuth(@RequestBody @Valid UpdateLoginRequest updateLoginRequest) {
        String auth = authService.updateLoginAuth(updateLoginRequest);

        if(auth != null) {
            return ResponseEntity.status(200).body(auth);
        } else {
            return ResponseEntity.status(400).body("로그인 실패");
        }
    }

    @PostMapping("/naver")
    public ResponseEntity<HashMap<Object, Object>> updateLoginOAuth(@RequestPart @Valid CreateOAuthRequest createOAuthRequest,
                                                                    @RequestPart(value="file", required = false) MultipartFile file) {
        AuthEntity authEntity;

        if(file != null && !file.isEmpty()) {
            authEntity = authService.createOAuth(createOAuthRequest, file);
        } else {
            authEntity = authService.createOAuth(createOAuthRequest, null);
        }

        String socialToken = jwtTokenProvider.createSocialToken(authEntity.getId());
        HashMap<Object, Object> result = new HashMap<>();

        result.put("네이버 로그인 결과", "회원가입 성공");
        result.put("accessToken", socialToken);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}