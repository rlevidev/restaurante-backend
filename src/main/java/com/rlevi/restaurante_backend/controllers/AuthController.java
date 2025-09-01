package com.rlevi.restaurante_backend.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rlevi.restaurante_backend.dto.AuthRequestDTO;
import com.rlevi.restaurante_backend.dto.AuthResponseDTO;
import com.rlevi.restaurante_backend.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody AuthRequestDTO request) {
        if ("admin".equals(request.getUsername()) && "admin123".equals(request.getPassword())) {
            String token = jwtUtil.generateToken(request.getUsername(), "ADMIN");
            return new AuthResponseDTO(token);
        }
        throw new RuntimeException("Credenciais inválidas");
    }

}
