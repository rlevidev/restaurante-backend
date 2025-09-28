package com.rlevi.restaurante_backend.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rlevi.restaurante_backend.domain.entities.Alimentos;
import com.rlevi.restaurante_backend.repository.AlimentosRepository;
import com.rlevi.restaurante_backend.shared.exception.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlimentosServiceTest {

    @Mock
    private AlimentosRepository alimentosRepository;

    @InjectMocks
    private AlimentosService alimentosService;

    private Alimentos alimento;

    @BeforeEach
    void setUp() {
        alimento = new Alimentos();
        alimento.setIdAlimento(1L);
        alimento.setNomeAlimento("Pizza Margherita");
        alimento.setPrecoAlimento(BigDecimal.valueOf(25.00));
        alimento.setDescricaoAlimento("Pizza com queijo e molho de tomate");
    }

    @Test
    void criarAlimento_DeveRetornarAlimentoSalvo() {
        // Arrange
        when(alimentosRepository.save(any(Alimentos.class))).thenReturn(alimento);

        // Act
        Alimentos resultado = alimentosService.criarAlimento(alimento);

        // Assert
        assertNotNull(resultado);
        assertEquals(alimento.getNomeAlimento(), resultado.getNomeAlimento());
        verify(alimentosRepository, times(1)).save(alimento);
    }

    @Test
    void atualizarAlimento_QuandoAlimentoExiste_DeveRetornarAlimentoAtualizado() {
        // Arrange
        Alimentos alimentoAtualizado = new Alimentos();
        alimentoAtualizado.setNomeAlimento("Pizza Calabresa");
        alimentoAtualizado.setPrecoAlimento(BigDecimal.valueOf(30.00));
        alimentoAtualizado.setDescricaoAlimento("Pizza com calabresa");

        when(alimentosRepository.findById(1L)).thenReturn(Optional.of(alimento));
        when(alimentosRepository.save(any(Alimentos.class))).thenReturn(alimento);

        // Act
        Alimentos resultado = alimentosService.atualizarAlimento(1L, alimentoAtualizado);

        // Assert
        assertNotNull(resultado);
        assertEquals("Pizza Calabresa", resultado.getNomeAlimento());
        assertEquals(BigDecimal.valueOf(30.00), resultado.getPrecoAlimento());
        verify(alimentosRepository, times(1)).findById(1L);
        verify(alimentosRepository, times(1)).save(alimento);
    }

    @Test
    void atualizarAlimento_QuandoAlimentoNaoExiste_DeveLancarResourceNotFoundException() {
        // Arrange
        when(alimentosRepository.findById(anyLong())).thenReturn(Optional.empty());

        Alimentos alimentoAtualizado = new Alimentos();

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            alimentosService.atualizarAlimento(1L, alimentoAtualizado);
        });
        verify(alimentosRepository, times(1)).findById(1L);
        verify(alimentosRepository, never()).save(any(Alimentos.class));
    }

    @Test
    void listarAlimentos_DeveRetornarListaDeAlimentos() {
        // Arrange
        List<Alimentos> alimentos = Arrays.asList(alimento);
        when(alimentosRepository.findAll()).thenReturn(alimentos);

        // Act
        List<Alimentos> resultado = alimentosService.listarAlimentos();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(alimento, resultado.get(0));
        verify(alimentosRepository, times(1)).findAll();
    }

    @Test
    void buscarAlimento_DeveRetornarOptionalComAlimento() {
        // Arrange
        when(alimentosRepository.findById(1L)).thenReturn(Optional.of(alimento));

        // Act
        Optional<Alimentos> resultado = alimentosService.buscarAlimento(1L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(alimento, resultado.get());
        verify(alimentosRepository, times(1)).findById(1L);
    }

    @Test
    void deletarAlimento_DeveChamarDeleteById() {
        // Act
        alimentosService.deletarAlimento(1L);

        // Assert
        verify(alimentosRepository, times(1)).deleteById(1L);
    }
}
