package com.example.project.auth.controller;

import com.example.project.auth.exception.ReadAuthorFail;
import com.example.project.auth.model.Auth;
import com.example.project.auth.service.AuthService;
import com.example.project.auth.view.AuthView;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequestMapping(value = "/v1/view")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthorReadController {

    private final AuthService authService;

    @Autowired
    public AuthorReadController(AuthService authService) {
        this.authService = authService;
    }

    @ApiResponses({
            @ApiResponse(code=201, message = "코스 작성자 정보 조회 완료"),
            @ApiResponse(code=401, message = "조회가 되지 않았습니다. 다시 확인하세요"),
            @ApiResponse(code=403, message = "권한이 존재하지 않습니다."),
            @ApiResponse(code=404, message = "데이터가 없습니다. 요청한 페이지를 찾을 수 없습니다.")
    })
    @ApiOperation(value = "코스 작성자 정보 조회할 때 쓰는 메소드")
    @ApiImplicitParam(name = "readAuthor", value = "작성자 정보 조회", dataType = "AuthVoReq")
    @GetMapping("/info")
    public AuthView readAuthor(@RequestParam(value = "id") String id, HttpServletRequest request) {
       log.info(id+"@@@@@@@@@@@@@@@@");
       log.info(authService.readAuthor(request)+"#########3####");
        if (id.equals("author-id")) {
            log.info(id);
            if (authService.readAuthor(request).equals(null)) {
                throw new ReadAuthorFail();
            } else {
                return new AuthView((Auth) authService.readAuthor(request));
            }
        }
        return null;
    }
}