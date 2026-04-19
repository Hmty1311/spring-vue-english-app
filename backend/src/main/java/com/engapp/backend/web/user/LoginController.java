package com.engapp.backend.web;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @PostMapping("/login")
    public String login() {
        System.out.println("🔥 LoginController: /api/auth/login endpoint hit");
        return "ok";
    }
}