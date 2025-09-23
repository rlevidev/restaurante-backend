package com.rlevi.restaurante_backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rlevi.restaurante_backend.service.PerfilService;
import com.rlevi.restaurante_backend.shared.dto.response.PerfilResponseDTO;

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
