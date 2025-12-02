package com.rlevi.restaurante_backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rlevi.restaurante_backend.domain.entities.Alimentos;
import com.rlevi.restaurante_backend.service.AlimentosService;
import com.rlevi.restaurante_backend.shared.exception.ResourceNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/alimentos")
@Tag(name = "Alimentos", description = "Endpoints para gestão e busca de alimentos")
public class AlimentosController {

    @Autowired
    private AlimentosService alimentosService;

    @PostMapping("/criar")
    @Operation(
        summary = "Cria um novo alimento",
        description = "Cria um novo alimento com base nos dados fornecidos."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Alimento criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Alimentos> criarAlimento(@Valid @RequestBody Alimentos alimentos) {
        Alimentos alimentoCriado = alimentosService.criarAlimento(alimentos);
        return new ResponseEntity<>(alimentoCriado, HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    @Operation(
        summary = "Lista todos os alimentos",
        description = "Lista todos os alimentos cadastrados no sistema."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Alimentos listados com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<Alimentos>> listarAlimentos() {
        List<Alimentos> alimentos = alimentosService.listarAlimentos();
        return ResponseEntity.ok(alimentos);
    }

    @GetMapping("/buscar/{id}")
    @Operation(
        summary = "Busca um alimento pelo ID",
        description = "Busca um alimento pelo ID e retorna os detalhes do alimento."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Alimento encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Alimento não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Alimentos> buscarAlimento(@PathVariable Long id) {
        Alimentos alimento = alimentosService.buscarAlimento(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alimento", id));
        return ResponseEntity.ok(alimento);
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(
        summary = "Deleta um alimento pelo ID",
        description = "Deleta um alimento pelo ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Alimento deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Alimento não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> deletarAlimento(@PathVariable Long id) {
        // Verificar se o alimento existe antes de deletar
        alimentosService.buscarAlimento(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alimento", id));

        alimentosService.deletarAlimento(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/atualizar/{id}")
    @Operation(
        summary = "Atualiza um alimento pelo ID",
        description = "Atualiza as informações de um alimento pelo ID com base nos dados fornecidos."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Alimento atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Alimento não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Alimentos> atualizarAlimento(@PathVariable Long id, @Valid @RequestBody Alimentos alimentos) {
        Alimentos alimentoAtualizado = alimentosService.atualizarAlimento(id, alimentos);
        return ResponseEntity.ok(alimentoAtualizado);
    }

}
