package com.rlevi.restaurante_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.rlevi.restaurante_backend.model.StatusPedido;

import lombok.Builder;

@Builder
public record PerfilResponseDTO(
                Long pedidoId,
                String nomeAlimento,
                Integer quantidade,
                BigDecimal precoUnitario,
                BigDecimal precoTotal,
                LocalDateTime dataCriacao,
                StatusPedido status) {
}
