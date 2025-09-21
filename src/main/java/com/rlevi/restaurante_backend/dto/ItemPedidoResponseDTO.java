package com.rlevi.restaurante_backend.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoResponseDTO {
    private Long idItem;
    private Long idAlimento;
    private String nomeAlimento;
    private BigDecimal precoUnitario;
    private Integer quantidade;
    private BigDecimal subtotal;
}