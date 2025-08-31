package com.rlevi.restaurante_backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidosRequestDTO {
    @NotNull(message = "Id do alimento deve ser informado")
    private Long idAlimento;
    @Min(value = 1, message = "Quantidade deve ser informada")
    private Integer quantidade;
    @NotBlank(message = "Nome do cliente deve ser informado")
    private String nomeCliente;
    @NotBlank(message = "Endereço do cliente deve ser informado")
    private String enderecoCliente;
    @NotBlank(message = "Telefone do cliente deve ser informado")
    private String telefoneCliente;
}
