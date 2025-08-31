package com.rlevi.restaurante_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rlevi.restaurante_backend.model.Pedidos;

@Repository
public interface PedidosRepository extends JpaRepository<Pedidos, Long>{

}
