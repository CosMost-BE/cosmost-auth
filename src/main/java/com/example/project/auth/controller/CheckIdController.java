package com.example.project.auth.controller;

import com.example.project.auth.exception.DuplicatedIdException;
import com.example.project.auth.service.AuthService;
import com.example.project.auth.service.AuthServiceImpl;
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

@Slf4j
@RequestMapping("/v1/validation")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CheckIdController {
    private final AuthService authService;
    private final AuthServiceImpl authServiceImpl;

    @Autowired
    public CheckIdController(AuthService authService, AuthServiceImpl authServiceImpl) {
        this.authService = authService;
        this.authServiceImpl = authServiceImpl;
    }

    @ApiResponses({
            @ApiResponse(code=201, message = "아이디 중복체크 완료"),
            @ApiResponse(code=401, message = "중복된 아이디입니다. 다시 확인하세요"),
            @ApiResponse(code=403, message = "권한이 존재하지 않습니다."),
            @ApiResponse(code=404, message = "데이터가 없습니다. 요청한 페이지를 찾을 수 없습니다.")
    })
    @ApiOperation(value = "중복 아이디를 확인할 때 쓰는 메소드")
    @GetMapping("/duplication")
    public ResponseEntity<?> checkId(@RequestParam(value="id") String id) {

        if(String.valueOf(id).equals(authService.checkId(id))) {
            throw new DuplicatedIdException();
        } else {
            return ResponseEntity.status(200).body("사용할 수 있는 아이디입니다.");
        }
    }
}
