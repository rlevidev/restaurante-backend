package com.rlevi.restaurante_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.rlevi.restaurante_backend.model.StatusPedido;

import lombok.Builder;

@Builder
public record PerfilResponseDTO(
        Long pedidoId,
        List<ItemPedidoResponseDTO> itens,
        BigDecimal precoTotal,
        String nomeCliente,
        String enderecoCliente,
        String telefoneCliente,
        LocalDateTime dataCriacao,
        StatusPedido status) {
}
