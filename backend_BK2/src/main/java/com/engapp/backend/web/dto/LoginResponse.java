package com.engapp.backend.web.dto;

import lombok.Data;

@Data
public class LoginResponse {

    private String token;
    private Long userId;
    private String userName;
    
}
