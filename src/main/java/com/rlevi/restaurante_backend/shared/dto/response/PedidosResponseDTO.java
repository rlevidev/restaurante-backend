package com.rlevi.restaurante_backend.shared.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.rlevi.restaurante_backend.domain.enums.StatusPedido;

public record PedidosResponseDTO(
        Long pedidoId,
        List<ItemPedidoResponseDTO> itens,
        BigDecimal precoTotal,
        String nomeCliente,
        String enderecoCliente,
        String telefoneCliente,
        LocalDateTime dataCriacao,
        StatusPedido status) {
}
