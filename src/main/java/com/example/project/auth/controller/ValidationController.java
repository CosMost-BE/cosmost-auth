package com.example.project.auth.controller;

import com.example.project.auth.exception.DuplicatedNickname;
import com.example.project.auth.exception.DuplicatedId;
import com.example.project.auth.requestbody.PutAuthRequest;
import com.example.project.auth.service.AuthService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    public ResponseEntity<?> checkId(@RequestBody @Valid HttpServletRequest request, Long id) {
//        if(id.equals(authService.checkId(id))) {
//            throw new DuplicatedId();
//        } else {
//            return ResponseEntity.status(200).body("사용할 수 있는 아이디입니다.");
//        }
        String auth = String.valueOf(authService.checkId(String.valueOf(request)));
        if(auth.equals(authService.checkId(String.valueOf(auth)))) {
            throw new DuplicatedId();
        } else {
            return ResponseEntity.status(200).body("사용할 수 있는 아이디입니다.");
        }
    }

//    @ApiOperation(value = "중복 닉네임을 확인할 때 쓰는 메소드")
//    @GetMapping("/duplicate")
//    public ResponseEntity<?> checkNickname(@RequestParam(value="nickname") String nickname) {
//        if(nickname.equals(authService.checkNickname(nickname))) {
//            throw new DuplicatedNickname();
//        } else {
//            return ResponseEntity.status(200).body("사용할 수 있는 닉네임입니다.");
//        }
//    }
}