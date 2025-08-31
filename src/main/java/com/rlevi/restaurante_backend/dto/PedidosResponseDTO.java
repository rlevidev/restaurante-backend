package com.rlevi.restaurante_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidosResponseDTO {
    private Long pedidoId;
    private String nomeAlimento;
    private Double precoTotal;
    private Integer quantidade;
}
