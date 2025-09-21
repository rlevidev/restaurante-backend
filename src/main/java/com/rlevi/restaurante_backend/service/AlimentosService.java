package com.rlevi.restaurante_backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rlevi.restaurante_backend.exception.ResourceNotFoundException;
import com.rlevi.restaurante_backend.model.Alimentos;
import com.rlevi.restaurante_backend.repository.AlimentosRepository;

@Service
public class AlimentosService {
    @Autowired
    private AlimentosRepository alimentosRepository;

    public Alimentos criarAlimento(Alimentos alimentos) {
        return alimentosRepository.save(alimentos);
    }

    public Alimentos atualizarAlimento(Long id, Alimentos alimentosAtualizados) {
        return alimentosRepository.findById(id).map(alimentoExistente -> {
            alimentoExistente.setNomeAlimento(alimentosAtualizados.getNomeAlimento());
            alimentoExistente.setPrecoAlimento(alimentosAtualizados.getPrecoAlimento());
            alimentoExistente.setDescricaoAlimento(alimentosAtualizados.getDescricaoAlimento());

            return alimentosRepository.save(alimentoExistente);
        })
                .orElseThrow(() -> new ResourceNotFoundException("Alimento", id));
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
