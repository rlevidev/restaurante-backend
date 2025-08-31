package com.rlevi.restaurante_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.rlevi.restaurante_backend.model.Alimentos;
import com.rlevi.restaurante_backend.repository.AlimentosRepository;

@Service
public class AlimentosService {
    @Autowired
    private AlimentosRepository alimentosRepository;

    public void criarAlimento(Alimentos alimentos) {
        alimentosRepository.save(alimentos);
    }

    public void atualizarAlimento(Long id, Alimentos alimentosAtualizados) {
        alimentosRepository.findById(id).map(alimentoExistente -> {
            alimentoExistente.setNomeAlimento(alimentosAtualizados.getNomeAlimento());
            alimentoExistente.setPrecoAlimento(alimentosAtualizados.getPrecoAlimento());
            alimentoExistente.setDescricaoAlimento(alimentosAtualizados.getDescricaoAlimento());

            return alimentosRepository.save(alimentoExistente);
        })
        .orElseThrow(() -> new RuntimeException("Alimento nao encontrado"));
    }

    public List<Alimentos> listarAlimentos() {
        return alimentosRepository.findAll();
    }

    public Optional<Alimentos> buscarAlimento(Long id) {
        return alimentosRepository.findById(id);
    }

    public void deletarAlimento(Long id) {
        alimentosRepository.deleteById(id);
    }
}
