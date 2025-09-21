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

import com.rlevi.restaurante_backend.exception.ResourceNotFoundException;
import com.rlevi.restaurante_backend.model.Alimentos;
import com.rlevi.restaurante_backend.service.AlimentosService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/alimentos")
public class AlimentosController {

    @Autowired
    private AlimentosService alimentosService;

    @PostMapping("/criar")
    public ResponseEntity<Alimentos> criarAlimento(@Valid @RequestBody Alimentos alimentos) {
        Alimentos alimentoCriado = alimentosService.criarAlimento(alimentos);
        return new ResponseEntity<>(alimentoCriado, HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Alimentos>> listarAlimentos() {
        List<Alimentos> alimentos = alimentosService.listarAlimentos();
        return ResponseEntity.ok(alimentos);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Alimentos> buscarAlimento(@PathVariable Long id) {
        Alimentos alimento = alimentosService.buscarAlimento(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alimento", id));
        return ResponseEntity.ok(alimento);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarAlimento(@PathVariable Long id) {
        // Verificar se o alimento existe antes de deletar
        alimentosService.buscarAlimento(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alimento", id));

        alimentosService.deletarAlimento(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Alimentos> atualizarAlimento(@PathVariable Long id, @Valid @RequestBody Alimentos alimentos) {
        Alimentos alimentoAtualizado = alimentosService.atualizarAlimento(id, alimentos);
        return ResponseEntity.ok(alimentoAtualizado);
    }

}
