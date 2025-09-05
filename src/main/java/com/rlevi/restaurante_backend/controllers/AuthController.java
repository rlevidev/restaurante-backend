package com.rlevi.restaurante_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rlevi.restaurante_backend.dto.LoginRequestDTO;
import com.rlevi.restaurante_backend.dto.LoginResponseDTO;
import com.rlevi.restaurante_backend.dto.RegisterRequestDTO;
import com.rlevi.restaurante_backend.model.Usuarios;
import com.rlevi.restaurante_backend.repository.UsuarioRepository;
import com.rlevi.restaurante_backend.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> register(@RequestBody RegisterRequestDTO registerRequest){
        Usuarios usuarios = usuarioRepository.save(new Usuarios(null, registerRequest.email(), registerRequest.nome(), passwordEncoder.encode(registerRequest.senha()), "ROLE_USER"));

        String token = jwtUtil.generateToken(usuarios.getNome(), usuarios.getRole());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest){
        Usuarios usuarios = usuarioRepository.findByNome(loginRequest.nome()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(loginRequest.senha(), usuarios.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        String token = jwtUtil.generateToken(usuarios.getNome(), usuarios.getRole());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
