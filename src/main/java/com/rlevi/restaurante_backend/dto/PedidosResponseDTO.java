package com.rlevi.restaurante_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.rlevi.restaurante_backend.model.StatusPedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PedidosResponseDTO {
    private Long pedidoId;
    private String nomeAlimento;
    private BigDecimal precoUnitario;
    private Integer quantidade;
    private BigDecimal precoTotal;
    private String nomeCliente;
    private String enderecoCliente;
    private String telefoneCliente;
    private LocalDateTime dataCriacao;
    private StatusPedido status;
}
