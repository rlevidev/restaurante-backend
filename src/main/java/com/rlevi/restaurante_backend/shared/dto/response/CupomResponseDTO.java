package com.rlevi.restaurante_backend.shared.dto.response;

public record CupomResponseDTO(
        Long id,
        String codigo,
        Double desconto,
        Integer quantidade,
        String tipoDesconto,
        String tipoQuantidade,
        String tipo
) {
}
