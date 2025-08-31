package com.rlevi.restaurante_backend.dto;

import java.time.LocalDateTime;

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
    private Double precoUnitario;
    private Integer quantidade;
    private Double precoTotal;
    private String nomeCliente;
    private String enderecoCliente;
    private String telefoneCliente;
    private LocalDateTime dataCriacao;
}
