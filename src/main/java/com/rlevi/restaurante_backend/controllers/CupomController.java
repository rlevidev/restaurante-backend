package com.rlevi.restaurante_backend.controllers;

import com.rlevi.restaurante_backend.domain.entities.Cupom;
import com.rlevi.restaurante_backend.repository.CupomRepository;
import com.rlevi.restaurante_backend.service.CupomService;
import com.rlevi.restaurante_backend.shared.dto.request.CupomRequestDTO;
import com.rlevi.restaurante_backend.shared.dto.response.CupomResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cupom")
@Tag(name = "Cupom", description = "Endpoints para gerenciamento de cupons")
public class CupomController {
  @Autowired
  private CupomService cupomService;

  @Autowired
  private CupomRepository cupomRepository;

  @PostMapping("/criar")
  @Operation(summary = "Criar cupom", description = "Cria um novo cupom")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Cupom criado com sucesso"),
    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  public ResponseEntity<CupomResponseDTO> criarCupom(@Valid @RequestBody CupomRequestDTO cupomRequest) {
    CupomResponseDTO cupom = cupomService.criarCupom(cupomRequest);
    return ResponseEntity.ok(cupom);
  }
}
