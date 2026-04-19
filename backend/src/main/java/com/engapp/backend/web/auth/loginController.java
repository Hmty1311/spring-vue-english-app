package com.engapp.backend.web.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class loginController {

    @GetMapping("/api/auth/login")
    public String login() {
        return "Login successful!";
    }
    
}
