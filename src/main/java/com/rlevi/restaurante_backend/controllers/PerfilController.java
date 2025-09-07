package com.rlevi.restaurante_backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import com.rlevi.restaurante_backend.dto.PerfilResponseDTO;
import com.rlevi.restaurante_backend.service.PerfilService;

@RestController
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    @GetMapping("/pedidos")
    public ResponseEntity<List<PerfilResponseDTO>> getPedidosUsuario(@AuthenticationPrincipal UserDetails userDetails) {
        List<PerfilResponseDTO> pedidos = perfilService.buscarPedidosPorUsuario(userDetails.getUsername());
        return ResponseEntity.ok(pedidos);
    }
}
