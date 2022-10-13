package com.example.project.auth.controller;

import com.example.project.auth.requestbody.ReadAuthRequest;
import com.example.project.auth.service.AuthService;
import io.swagger.annotations.ApiImplicitParam;
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
import javax.validation.Valid;

@Slf4j
@RequestMapping("/v1/validation")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CheckIdController {
    private final AuthService authService;

    @Autowired
    public CheckIdController(AuthService authService) {
        this.authService = authService;
    }

    @ApiResponses({
            @ApiResponse(code=201, message = "아이디 중복체크 완료"),
            @ApiResponse(code=401, message = "중복된 아이디입니다. 다시 확인하세요"),
            @ApiResponse(code=403, message = "권한이 존재하지 않습니다."),
            @ApiResponse(code=404, message = "데이터가 없습니다. 요청한 페이지를 찾을 수 없습니다.")
    })

    @ApiOperation(value = "중복 아이디를 확인할 때 쓰는 메소드")
    @ApiImplicitParam(name = "checkId", value = "중복된 아이디 확인", dataType = "CheckIdVoReq")
    @GetMapping("/duplication")
    public ResponseEntity<String> checkId(@RequestBody @Valid ReadAuthRequest readAuthRequest) {
        authService.checkId(String.valueOf(readAuthRequest));
        return ResponseEntity.ok().body("사용할 수 있는 아이디입니다.");
    }
}
