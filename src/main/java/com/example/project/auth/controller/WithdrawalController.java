package com.example.project.auth.controller;

import com.example.project.auth.configuration.util.JwtTokenProvider;
import com.example.project.auth.requestbody.DeleteAuthRequest;
import com.example.project.auth.service.AuthService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RequestMapping("/v1/auths")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class WithdrawalController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public WithdrawalController(AuthService authService, JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @ApiResponses({
            @ApiResponse(code=201, message = "회원탈퇴 완료"),
            @ApiResponse(code=401, message = "회원탈퇴가 되지 않았습니다. 다시 확인하세요"),
            @ApiResponse(code=403, message = "권한이 존재하지 않습니다."),
            @ApiResponse(code=404, message = "데이터가 없습니다. 요청한 페이지를 찾을 수 없습니다.")
    })

    @ApiOperation(value = "회원탈퇴를 할 때 쓰는 메소드")
    @ApiImplicitParam(name = "a", value = "회원탈퇴", dataType = "WithdrawlVoReq")
    @PutMapping("")
    public ResponseEntity<String> putAuth(@RequestBody @Valid DeleteAuthRequest deleteAuthRequest, HttpServletRequest request) {
        String token = jwtTokenProvider.getToken(request);
        Long id = Long.valueOf(jwtTokenProvider.getUserPk(token));
        return authService.deleteAuth(id, deleteAuthRequest);
    }
}
