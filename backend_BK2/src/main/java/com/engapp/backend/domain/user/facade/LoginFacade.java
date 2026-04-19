package com.engapp.backend.domain.user.facade;

import org.springframework.stereotype.Component;

import com.engapp.backend.domain.user.service.LoginService;
import com.engapp.backend.web.dto.LoginResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginFacade {

    private final LoginService loginService;

    public LoginResponse login(String userName, String password) {
        
        return loginService.login(userName, password);
    }

}
