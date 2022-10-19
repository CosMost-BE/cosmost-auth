package com.example.project.auth.controller;

import com.example.project.auth.requestbody.UpdateAuthRequest;
<<<<<<< HEAD
=======
import com.example.project.auth.requestbody.UpdateLoginRequest;
>>>>>>> c1d18f9a064f0a4ea17163e087ea14556a3c2f57
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

<<<<<<< HEAD
import javax.servlet.http.HttpServletRequest;
=======
>>>>>>> c1d18f9a064f0a4ea17163e087ea14556a3c2f57
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
    public ResponseEntity<?> updateAuth(@RequestBody @Valid UpdateAuthRequest updateAuthRequest) {
//        authService.updateAuthInfo(updateAuthRequest);
//        return ResponseEntity.ok().body("회원정보 수정이 되었습니다.");
    }
}