package com.rlevi.restaurante_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rlevi.restaurante_backend.model.Pedidos;
import com.rlevi.restaurante_backend.model.Usuarios;

@Repository
public interface PedidosRepository extends JpaRepository<Pedidos, Long>{

    List<Pedidos> findByUsuarioOrderByDataCriacaoDesc(Usuarios usuario);

}
