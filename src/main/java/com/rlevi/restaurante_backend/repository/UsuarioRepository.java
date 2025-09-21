package com.rlevi.restaurante_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rlevi.restaurante_backend.model.Usuarios;

public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {
    Optional<Usuarios> findByNome(String nome);

    Optional<Usuarios> findByEmail(String email);

    boolean existsByNome(String nome);

    boolean existsByEmail(String email);
}
