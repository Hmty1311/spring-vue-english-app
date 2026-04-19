package com.engapp.backend.web.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engapp.backend.domain.user.facade.LoginFacade;
import com.engapp.backend.web.dto.LoginRequest;
import com.engapp.backend.web.dto.LoginResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

    private final LoginFacade loginFacade;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
       
        return loginFacade.login(request.getUserName(), request.getPassword());
    }
}