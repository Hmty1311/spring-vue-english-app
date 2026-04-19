package com.engapp.backend.web.login;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engapp.backend.domain.user.facade.LoginFacade;
import com.engapp.backend.web.login.dto.LoginRequest;
import com.engapp.backend.web.login.dto.LoginResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final LoginFacade loginFacade;

    public LoginController(LoginFacade loginFacade) {
        this.loginFacade = loginFacade;
    }

    @PostMapping
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        return loginFacade.login(
            request.getLoginId(),
            request.getPassword()    
        );
    }
    
}
