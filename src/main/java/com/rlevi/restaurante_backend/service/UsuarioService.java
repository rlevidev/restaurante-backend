package com.rlevi.restaurante_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rlevi.restaurante_backend.model.Usuarios;
import com.rlevi.restaurante_backend.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Object findByNome(String nome) {
        return usuarioRepository.findByNome(nome);
    }

    public Usuarios save(Usuarios usuarios) {
        return usuarioRepository.save(usuarios);
    }
   
}
