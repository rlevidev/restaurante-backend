package com.rlevi.restaurante_backend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlimentosResponseDTO {
    private Long idAlimento;
    private String nomeAlimento;
    private Double precoAlimento;
    private String descricaoAlimento;
}
