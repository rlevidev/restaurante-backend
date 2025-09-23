package com.rlevi.restaurante_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rlevi.restaurante_backend.domain.entities.ItemPedido;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}