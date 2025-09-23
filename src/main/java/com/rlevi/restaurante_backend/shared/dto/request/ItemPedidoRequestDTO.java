package com.rlevi.restaurante_backend.shared.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemPedidoRequestDTO(
        @NotNull(message = "ID do alimento é obrigatório") Long idAlimento,

        @NotNull(message = "Quantidade é obrigatória") @Min(value = 1, message = "Quantidade deve ser pelo menos 1") Integer quantidade) {
}
