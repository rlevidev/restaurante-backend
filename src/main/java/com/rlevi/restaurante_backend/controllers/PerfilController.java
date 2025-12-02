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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/perfil")
@Tag(name = "Perfil", description = "Obter informações do perfil do usuário logado")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    @GetMapping("/pedidos")
    @Operation(
        summary = "Obter pedidos do usuário logado",
        description = "Obter pedidos do usuário logado"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedidos obtidos com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<PerfilResponseDTO>> getPedidosUsuario(@AuthenticationPrincipal UserDetails userDetails) {
        List<PerfilResponseDTO> pedidos = perfilService.buscarPedidosPorUsuario(userDetails.getUsername());
        return ResponseEntity.ok(pedidos);
    }
}
