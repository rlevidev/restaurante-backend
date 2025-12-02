package com.rlevi.restaurante_backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rlevi.restaurante_backend.domain.entities.Usuarios;
import com.rlevi.restaurante_backend.domain.enums.StatusPedido;
import com.rlevi.restaurante_backend.repository.UsuarioRepository;
import com.rlevi.restaurante_backend.service.PedidosService;
import com.rlevi.restaurante_backend.shared.dto.request.PedidosRequestDTO;
import com.rlevi.restaurante_backend.shared.dto.response.PedidosResponseDTO;
import com.rlevi.restaurante_backend.shared.exception.ResourceNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedidos", description = "Endpoints para gerenciamento e criação de pedidos")
public class PedidosController {

    @Autowired
    private PedidosService pedidosService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/criar")
    @Operation(
        summary = "Criar novo pedido",
        description = "Cria um novo pedido com base nos itens fornecidos."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<PedidosResponseDTO> criarPedido(@Valid @RequestBody PedidosRequestDTO pedidosRequestDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        Usuarios usuario = usuarioRepository.findByNome(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", "nome", userDetails.getUsername()));

        PedidosResponseDTO pedido = pedidosService.criarPedido(
                pedidosRequestDTO.itens(),
                pedidosRequestDTO.nomeCliente(),
                pedidosRequestDTO.enderecoCliente(),
                pedidosRequestDTO.telefoneCliente(),
                usuario);
        return new ResponseEntity<>(pedido, HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    @Operation(
        summary = "Listar todos os pedidos",
        description = "Listar todos os pedidos cadastrados no sistema."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedidos listados com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<PedidosResponseDTO>> listarPedidos() {
        List<PedidosResponseDTO> pedidos = pedidosService.listarPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(
        summary = "Deletar um pedido pelo ID",
        description = "Deletar um pedido pelo ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Pedido deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    }
    )
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        pedidosService.deletarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{pedidoId}/status")
    @Operation(
        summary = "Atualiza o status de um pedido manualmente",
        description = "Atualiza o status de um pedido pelo ID com base nos dados fornecidos."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    }
    )
    public ResponseEntity<PedidosResponseDTO> atualizarStatus(@PathVariable Long pedidoId,
            @RequestBody StatusPedido novoStatus) {
        PedidosResponseDTO atualizado = pedidosService.atualizarStatusManual(pedidoId, novoStatus);
        return ResponseEntity.ok(atualizado);
    }

    @PatchMapping("/{pedidoId}/status/avancar")
    @Operation(
        summary = "Avançar o status de um pedido em ordem definida",
        description = "Avançar o status de um pedido em uma ordem pre-definida pelo ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status avançado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    }
    )
    public ResponseEntity<StatusPedido> avancarStatusAutomatico(@PathVariable Long pedidoId) {
        StatusPedido novoStatus = pedidosService.avancarStatusAutomatico(pedidoId);
        return ResponseEntity.ok(novoStatus);
    }
}
