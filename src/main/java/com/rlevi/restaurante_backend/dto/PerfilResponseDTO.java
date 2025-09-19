package com.rlevi.restaurante_backend.dto;

import java.time.LocalDateTime;

import com.rlevi.restaurante_backend.model.StatusPedido;

import lombok.Builder;

@Builder
public record PerfilResponseDTO(
        Long pedidoId,
        String nomeAlimento,
        Integer quantidade,
        Double precoUnitario,
        Double precoTotal,
        LocalDateTime dataCriacao,
        StatusPedido status) {
}
