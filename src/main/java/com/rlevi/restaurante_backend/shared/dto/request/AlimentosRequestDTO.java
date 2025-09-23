package com.rlevi.restaurante_backend.shared.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AlimentosRequestDTO(
        @NotBlank(message = "Nome do alimento é obrigatório") @Size(min = 2, max = 100, message = "Nome do alimento deve ter entre 2 e 100 caracteres") String nomeAlimento,

        @NotNull(message = "Preço do alimento é obrigatório") @DecimalMin(value = "0.01", message = "Preço do alimento deve ser maior que zero") @Digits(integer = 8, fraction = 2, message = "Preço deve ter no máximo 8 dígitos inteiros e 2 decimais") BigDecimal precoAlimento,

        String descricaoAlimento) {
}
