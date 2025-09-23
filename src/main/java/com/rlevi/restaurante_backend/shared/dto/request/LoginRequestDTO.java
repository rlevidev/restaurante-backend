package com.rlevi.restaurante_backend.shared.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(
        @NotBlank(message = "Nome é obrigatório") @Size(min = 3, max = 50, message = "Nome deve ter entre 3 e 50 caracteres") String nome,

        @NotBlank(message = "Senha é obrigatória") @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres") String senha) {
}
