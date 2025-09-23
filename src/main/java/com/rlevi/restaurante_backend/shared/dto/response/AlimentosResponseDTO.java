package com.rlevi.restaurante_backend.shared.dto.response;

import java.math.BigDecimal;

public record AlimentosResponseDTO(
        Long idAlimento,
        String nomeAlimento,
        BigDecimal precoAlimento,
        String descricaoAlimento) {
}
