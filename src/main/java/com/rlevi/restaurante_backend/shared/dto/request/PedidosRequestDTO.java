package com.rlevi.restaurante_backend.shared.dto.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PedidosRequestDTO(
        @NotEmpty(message = "O pedido deve conter pelo menos um alimento") @Valid List<ItemPedidoRequestDTO> itens,

        @NotBlank(message = "Nome do cliente é obrigatório") @Size(min = 2, max = 100, message = "Nome do cliente deve ter entre 2 e 100 caracteres") String nomeCliente,

        @NotBlank(message = "Endereço do cliente é obrigatório") @Size(min = 10, max = 200, message = "Endereço deve ter entre 10 e 200 caracteres") String enderecoCliente,

        @NotBlank(message = "Telefone do cliente é obrigatório") @Pattern(regexp = "^\\(?\\d{2}\\)?[\\s-]?\\d{4,5}[\\s-]?\\d{4}$", message = "Telefone deve estar no formato (xx) xxxxx-xxxx ou similar") String telefoneCliente) {
}
