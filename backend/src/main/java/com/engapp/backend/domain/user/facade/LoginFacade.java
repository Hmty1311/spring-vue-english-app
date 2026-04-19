package com.engapp.backend.domain.user.facade;

import org.springframework.stereotype.Component;

import com.engapp.backend.domain.user.model.User;
import com.engapp.backend.domain.user.service.LoginService;
import com.engapp.backend.web.login.dto.LoginResponse;

@Component
public class LoginFacade {

    private final LoginService loginService;

    public LoginFacade(LoginService loginService) {
        this.loginService = loginService;
    }

    public LoginResponse login(String loginId, String password) {

        User user = loginService.authenticate(loginId, password);

        //TODO: JWTはダミー
        String token = "dummy-token";

        return new LoginResponse(
            token,
            user.getId(),
            user.getUserName()
        );
    }

}
