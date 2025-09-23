package com.rlevi.restaurante_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rlevi.restaurante_backend.domain.entities.Alimentos;

@Repository
public interface AlimentosRepository extends JpaRepository<Alimentos, Long> {

}
