package com.example.project.auth.controller;

import com.example.project.auth.requestbody.PutAuthRequest;
import com.example.project.auth.service.AuthService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/v1/signin")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {
    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }
    @ApiResponses({
            @ApiResponse(code=201, message = "로그인 완료"),
            @ApiResponse(code=401, message = "로그인이 되지 않았습니다. 다시 확인하세요"),
            @ApiResponse(code=403, message = "권한이 존재하지 않습니다."),
            @ApiResponse(code=404, message = "데이터가 없습니다. 요청한 페이지를 찾을 수 없습니다.")
    })

    @ApiOperation(value = "로그인 할 때 쓰는 메소드")
    @ApiImplicitParam(name = "login", value = "로그인", dataType = "LoginVoReq")
    @PutMapping("")
    public ResponseEntity<String> putAuth(@RequestBody @Valid PutAuthRequest putAuthRequest) {
        String auth = authService.putAuth(putAuthRequest);

        if(auth != null) {
            return ResponseEntity.status(200).body(auth);
        } else {
            return ResponseEntity.status(400).body("로그인 실패");
        }
    }
}
