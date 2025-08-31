package com.rlevi.restaurante_backend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlimentosRequestDTO {
    @NotBlank(message = "Nome do alimento deve ser informado")
    private String nomeAlimento;
    @NotNull(message = "Preço do alimento deve ser informado")
    @Min(value = 0, message = "Preço do alimento deve ser maior que 0")
    private Double precoAlimento;
    private String descricaoAlimento;
}
