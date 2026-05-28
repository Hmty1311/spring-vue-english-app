package com.engapp.backend.domain.user.facade;

import org.springframework.stereotype.Component;

import com.engapp.backend.common.config.JwtProvider;
import com.engapp.backend.domain.user.model.User;
import com.engapp.backend.domain.user.service.LoginService;
import com.engapp.backend.web.login.dto.LoginResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginFacade {

    private final LoginService loginService;
    private final JwtProvider jwtProvider;

    public LoginResponse login(String loginId, String password) {

        User user = loginService.authenticate(loginId, password);

        String token = jwtProvider.generateToken(user);

        return new LoginResponse(
            token,
            user.getId(),
            user.getUserName()
        );
    }

}
