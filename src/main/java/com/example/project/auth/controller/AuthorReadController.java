package com.example.project.auth.controller;

import com.example.project.auth.exception.ReadAuthorFail;
import com.example.project.auth.model.Auth;
import com.example.project.auth.service.AuthService;
import com.example.project.auth.view.AuthView;
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

    @GetMapping("/info")
    public AuthView readAuthor(@RequestParam(value = "id") String id, HttpServletRequest request) {
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