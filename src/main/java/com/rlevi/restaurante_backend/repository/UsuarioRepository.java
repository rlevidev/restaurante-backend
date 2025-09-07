package com.rlevi.restaurante_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rlevi.restaurante_backend.model.Usuarios;

public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {
    Optional<Usuarios> findByNome(String nome);
    boolean existsByNome(String nome);
}
