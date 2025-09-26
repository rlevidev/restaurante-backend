package com.rlevi.restaurante_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rlevi.restaurante_backend.domain.entities.Usuarios;
import com.rlevi.restaurante_backend.domain.enums.Roles;
import com.rlevi.restaurante_backend.repository.UsuarioRepository;
import com.rlevi.restaurante_backend.security.JwtUtil;
import com.rlevi.restaurante_backend.shared.dto.request.LoginRequestDTO;
import com.rlevi.restaurante_backend.shared.dto.response.LoginResponseDTO;
import com.rlevi.restaurante_backend.shared.dto.response.RegisterRequestDTO;
import com.rlevi.restaurante_backend.shared.exception.AuthenticationException;
import com.rlevi.restaurante_backend.shared.exception.DuplicateResourceException;

import jakarta.validation.Valid;

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
    public ResponseEntity<LoginResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequest) {
        // Verificar se o email já existe
        if (usuarioRepository.existsByEmail(registerRequest.email())) {
            throw new DuplicateResourceException("Usuário", "email", registerRequest.email());
        }

        // Verificar se o nome já existe
        if (usuarioRepository.existsByNome(registerRequest.nome())) {
            throw new DuplicateResourceException("Usuário", "nome", registerRequest.nome());
        }

        Usuarios usuarios = new Usuarios();
        usuarios.setEmail(registerRequest.email());
        usuarios.setNome(registerRequest.nome());
        usuarios.setSenha(passwordEncoder.encode(registerRequest.senha()));
        usuarios.setRole(Roles.USER);

        usuarios = usuarioRepository.save(usuarios);

        String token = jwtUtil.generateToken(usuarios.getNome(), "ROLE_" + usuarios.getRole().name());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        Usuarios usuarios = usuarioRepository.findByNome(loginRequest.nome())
                .orElseThrow(() -> new AuthenticationException("Usuário não encontrado"));

        if (!passwordEncoder.matches(loginRequest.senha(), usuarios.getSenha())) {
            throw new AuthenticationException("Senha inválida");
        }

        String token = jwtUtil.generateToken(usuarios.getNome(), "ROLE_" + usuarios.getRole().name());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
