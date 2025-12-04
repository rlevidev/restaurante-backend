package com.rlevi.restaurante_backend.shared.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
        @NotBlank(message = "Email é obrigatório") @Email(message = "Email deve ter um formato válido") @Size(max = 100, message = "Email não pode exceder 100 caracteres")
        @Schema(example = "rlevidev@examplemail.com")
        String email,

        @NotBlank(message = "Nome é obrigatório") @Size(min = 3, max = 50, message = "Nome deve ter entre 3 e 50 caracteres") 
        @Schema(example = "rlevidev")
        String nome,

        @NotBlank(message = "Senha é obrigatória") @Size(min = 6, max = 100, message = "Senha deve ter entre 6 e 100 caracteres") 
        @Schema(example = "rlevidev_123456")
        String senha) {
}
