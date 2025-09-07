package com.rlevi.restaurante_backend.security;

import com.rlevi.restaurante_backend.model.Usuarios;
import com.rlevi.restaurante_backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuarios usuario = usuarioRepository.findByNome(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com nome: " + username));

        String roleSemPrefix = usuario.getRole().replace("ROLE_", "");
        
        return User.builder()
                .username(usuario.getNome())
                .password(usuario.getSenha())
                .roles(roleSemPrefix)
                .build();
    }
}