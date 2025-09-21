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

import com.rlevi.restaurante_backend.dto.PedidosRequestDTO;
import com.rlevi.restaurante_backend.dto.PedidosResponseDTO;
import com.rlevi.restaurante_backend.exception.ResourceNotFoundException;
import com.rlevi.restaurante_backend.model.StatusPedido;
import com.rlevi.restaurante_backend.model.Usuarios;
import com.rlevi.restaurante_backend.repository.UsuarioRepository;
import com.rlevi.restaurante_backend.service.PedidosService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pedidos")
public class PedidosController {

    @Autowired
    private PedidosService pedidosService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/criar")
    public ResponseEntity<PedidosResponseDTO> criarPedido(@Valid @RequestBody PedidosRequestDTO pedidosRequestDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        Usuarios usuario = usuarioRepository.findByNome(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", "nome", userDetails.getUsername()));

        PedidosResponseDTO pedido = pedidosService.criarPedido(
                pedidosRequestDTO.getItens(),
                pedidosRequestDTO.getNomeCliente(),
                pedidosRequestDTO.getEnderecoCliente(),
                pedidosRequestDTO.getTelefoneCliente(),
                usuario);
        return new ResponseEntity<>(pedido, HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PedidosResponseDTO>> listarPedidos() {
        List<PedidosResponseDTO> pedidos = pedidosService.listarPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        pedidosService.deletarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{pedidoId}/status")
    public ResponseEntity<PedidosResponseDTO> atualizarStatus(@PathVariable Long pedidoId,
            @RequestBody StatusPedido novoStatus) {
        PedidosResponseDTO atualizado = pedidosService.atualizarStatusManual(pedidoId, novoStatus);
        return ResponseEntity.ok(atualizado);
    }

    @PatchMapping("/{pedidoId}/status/avancar")
    public ResponseEntity<StatusPedido> avancarStatusAutomatico(@PathVariable Long pedidoId) {
        StatusPedido novoStatus = pedidosService.avancarStatusAutomatico(pedidoId);
        return ResponseEntity.ok(novoStatus);
    }
}
