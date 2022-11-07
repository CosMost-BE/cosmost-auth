package com.example.project.auth.controller;

import com.example.project.auth.exception.TypeNotFound;
import com.example.project.auth.infrastructure.entity.AuthEntity;
import com.example.project.auth.requestbody.CreateAuthRequest;
import com.example.project.auth.requestbody.UpdateAuthRequest;
import com.example.project.auth.service.AuthService;
import com.example.project.auth.view.AuthView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RequestMapping(value = "/v1/auths")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("")
    public ResponseEntity<String> createAuth(@RequestPart @Valid CreateAuthRequest createAuthRequest,
                                             @RequestPart(value="file", required = false) MultipartFile file) {
        authService.createAuth(createAuthRequest, file);
        return ResponseEntity.ok().body("회원가입이 되었습니다.");
    }

    @PutMapping("")
    public ResponseEntity<?> updateAuthInfo(@RequestPart @Valid UpdateAuthRequest updateAuthRequest, HttpServletRequest request,
                                            @RequestPart(value="file", required = false) MultipartFile file) {

        if (updateAuthRequest.getType().equals("회원정보 수정")) {
            authService.updateAuthInfo(updateAuthRequest, request, file);
            return ResponseEntity.ok("회원정보가 수정되었습니다.");

        } else if (updateAuthRequest.getType().equals("회원 탈퇴")) {
            authService.deleteAuthInfo(request, updateAuthRequest, file);
            return ResponseEntity.ok("회원탈퇴가 되었습니다.");

        } else if (updateAuthRequest.getType().equals("비밀번호 수정")) {
            authService.updatePassword(updateAuthRequest, request, file);
            return ResponseEntity.ok("비밀번호가 수정되었습니다.");

        } else {
            throw new TypeNotFound();
        }
    }



    // 사용자 조회
    @GetMapping("")
    public AuthView readAuth(HttpServletRequest request) {
        return new AuthView(authService.readAuth(request));
    }
}