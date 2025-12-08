package com.rlevi.restaurante_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rlevi.restaurante_backend.domain.entities.Cupom;

public interface CupomRepository extends JpaRepository<Cupom, Long>{
    Optional<Cupom> findByCodeIgnoreCase(String code);
    boolean existsByCode(String code);
}
