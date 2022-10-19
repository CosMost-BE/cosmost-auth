package com.example.project.auth.controller;

import com.example.project.auth.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RequestMapping("/v1/validation")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ValidationController {
    private final AuthService authService;

    @Autowired
    public ValidationController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/duplicate")
    public ResponseEntity<?> checkId(@RequestParam(value = "id") String id,
                                     HttpServletRequest request) {
        Boolean auth = authService.checkId(request);
        if (id.equals("login-id")) {
            if (authService.checkId(request).equals(true)) {
                return ResponseEntity.status(200).body("사용할 수 있는 아이디입니다.");
            } else {
                throw new DuplicatedId();
            }
        } else if (id.equals("nickname")) {
            if (authService.checkNickname(request).equals(true)) {
                return ResponseEntity.status(200).body("사용할 수 있는 닉네임입니다.");
        }
        return null;
    }
}