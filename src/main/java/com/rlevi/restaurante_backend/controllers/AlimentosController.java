package com.rlevi.restaurante_backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
        alimentosService.criarAlimento(alimentos);
        return ResponseEntity.ok(alimentos);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Alimentos>> listarAlimentos() {
        List<Alimentos> alimentos = alimentosService.listarAlimentos();
        return ResponseEntity.ok(alimentos);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Alimentos> buscarAlimento(@PathVariable Long id) {
        Optional<Alimentos> alimentos = alimentosService.buscarAlimento(id);
        return ResponseEntity.ok(alimentos.get());
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarAlimento(@PathVariable Long id) {
        try {
            alimentosService.deletarAlimento(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Alimentos> atualizarAlimento(@PathVariable Long id, @Valid @RequestBody Alimentos alimentos) {
        try {
            alimentosService.atualizarAlimento(id, alimentos);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
