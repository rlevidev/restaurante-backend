package com.rlevi.restaurante_backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rlevi.restaurante_backend.dto.PedidosRequestDTO;
import com.rlevi.restaurante_backend.dto.PedidosResponseDTO;
import com.rlevi.restaurante_backend.service.PedidosService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pedidos")
public class PedidosController {

    @Autowired
    private PedidosService pedidosService;

    @PostMapping("/criar")
    public ResponseEntity<PedidosResponseDTO> criarPedido(@Valid @RequestBody PedidosRequestDTO pedidosRequestDTO) {
        PedidosResponseDTO pedido = pedidosService.criarPedido(
            pedidosRequestDTO.getIdAlimento(),
            pedidosRequestDTO.getQuantidade(),
            pedidosRequestDTO.getNomeCliente(),
            pedidosRequestDTO.getEnderecoCliente(),
            pedidosRequestDTO.getTelefoneCliente()
        );
        return ResponseEntity.ok(pedido);
    }
    
    @GetMapping("/listar")
    public ResponseEntity<List<PedidosResponseDTO>> listarPedidos() {
        List<PedidosResponseDTO> pedidos = pedidosService.listarPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        try {
            pedidosService.deletarPedido(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
