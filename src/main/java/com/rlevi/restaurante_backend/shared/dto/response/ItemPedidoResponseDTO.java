package com.rlevi.restaurante_backend.shared.dto.response;

import java.math.BigDecimal;

public record ItemPedidoResponseDTO(
        Long idItem,
        Long idAlimento,
        String nomeAlimento,
        BigDecimal precoUnitario,
        Integer quantidade,
        BigDecimal subtotal) {
}
