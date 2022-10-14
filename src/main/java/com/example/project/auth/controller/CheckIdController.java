package com.example.project.auth.controller;

import com.example.project.auth.exception.DuplicatedIdException;
import com.example.project.auth.infrastructure.repository.AuthEntityRepository;
import com.example.project.auth.responsebody.ReadAuthResponse;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RequestMapping("/v1/validation")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CheckIdController {
    private final AuthService authService;

    private final AuthEntityRepository authEntityRepository;

    @Autowired
    public CheckIdController(AuthService authService, AuthEntityRepository authEntityRepository) {
        this.authService = authService;
        this.authEntityRepository = authEntityRepository;
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
    public ResponseEntity<String> checkId(@RequestBody @Valid ReadAuthResponse readAuthResponse) {

        if (authService.checkId(readAuthResponse)) {
            return ResponseEntity.status(200).body("사용할 수 있습니다.");
        } else {
            return ResponseEntity.status(200).body("중복된 아이디 입니다.");
        }

    }
}
