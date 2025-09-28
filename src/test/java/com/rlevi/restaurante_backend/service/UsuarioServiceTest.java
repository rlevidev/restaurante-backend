package com.rlevi.restaurante_backend.service;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rlevi.restaurante_backend.domain.entities.Usuarios;
import com.rlevi.restaurante_backend.repository.UsuarioRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuarios usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuarios();
        usuario.setIdUsuario(1L);
        usuario.setNome("João Silva");
        usuario.setEmail("joao@email.com");
    }

    @Test
    void findByNome_QuandoUsuarioExiste_DeveRetornarUsuario() {
        // Arrange
        when(usuarioRepository.findByNome("João Silva")).thenReturn(Optional.of(usuario));

        // Act
        Object resultado = usuarioService.findByNome("João Silva");

        // Assert
        assertNotNull(resultado);
        assertEquals(Optional.of(usuario), resultado);
        verify(usuarioRepository, times(1)).findByNome("João Silva");
    }

    @Test
    void findByNome_QuandoUsuarioNaoExiste_DeveRetornarOptionalVazio() {
        // Arrange
        when(usuarioRepository.findByNome(anyString())).thenReturn(Optional.empty());

        // Act
        Object resultado = usuarioService.findByNome("Maria");

        // Assert
        assertEquals(Optional.empty(), resultado);
        verify(usuarioRepository, times(1)).findByNome("Maria");
    }

    @Test
    void save_DeveRetornarUsuarioSalvo() {
        // Arrange
        when(usuarioRepository.save(any(Usuarios.class))).thenReturn(usuario);

        // Act
        Usuarios resultado = usuarioService.save(usuario);

        // Assert
        assertNotNull(resultado);
        assertEquals(usuario.getNome(), resultado.getNome());
        assertEquals(usuario.getEmail(), resultado.getEmail());
        verify(usuarioRepository, times(1)).save(usuario);
    }
}
