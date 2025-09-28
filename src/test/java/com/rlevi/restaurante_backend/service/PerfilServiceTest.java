package com.rlevi.restaurante_backend.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.rlevi.restaurante_backend.domain.entities.Alimentos;
import com.rlevi.restaurante_backend.domain.entities.ItemPedido;
import com.rlevi.restaurante_backend.domain.entities.Pedidos;
import com.rlevi.restaurante_backend.domain.entities.Usuarios;
import com.rlevi.restaurante_backend.domain.enums.StatusPedido;
import com.rlevi.restaurante_backend.repository.PedidosRepository;
import com.rlevi.restaurante_backend.repository.UsuarioRepository;
import com.rlevi.restaurante_backend.shared.dto.response.PerfilResponseDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PerfilServiceTest {

    @Mock
    private PedidosRepository pedidosRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private PerfilService perfilService;

    private Usuarios usuario;
    private Pedidos pedido;
    private ItemPedido itemPedido;

    @BeforeEach
    void setUp() {
        usuario = new Usuarios();
        usuario.setIdUsuario(1L);
        usuario.setNome("João Silva");

        Alimentos alimento = new Alimentos();
        alimento.setIdAlimento(1L);
        alimento.setNomeAlimento("Pizza Margherita");

        itemPedido = new ItemPedido();
        itemPedido.setIdItem(1L);
        itemPedido.setAlimento(alimento);
        itemPedido.setQuantidade(2);
        itemPedido.setPrecoUnitario(BigDecimal.valueOf(25.00));
        itemPedido.setSubtotal(BigDecimal.valueOf(50.00));

        pedido = new Pedidos();
        pedido.setPedidoId(1L);
        pedido.setNomeCliente("João Silva");
        pedido.setEnderecoCliente("Rua A, 123");
        pedido.setTelefoneCliente("11999999999");
        pedido.setUsuario(usuario);
        pedido.setStatus(StatusPedido.RECEBIDO);
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setPrecoTotal(BigDecimal.valueOf(50.00));
        pedido.setItens(Arrays.asList(itemPedido));
    }

    @Test
    void buscarPedidosPorUsuario_QuandoUsuarioExiste_DeveRetornarListaDePerfilResponseDTO() {
        // Arrange
        List<Pedidos> pedidos = Arrays.asList(pedido);
        when(usuarioRepository.findByNome("João Silva")).thenReturn(Optional.of(usuario));
        when(pedidosRepository.findByUsuarioOrderByDataCriacaoDesc(usuario)).thenReturn(pedidos);

        // Act
        List<PerfilResponseDTO> resultado = perfilService.buscarPedidosPorUsuario("João Silva");

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("João Silva", resultado.get(0).nomeCliente());
        assertEquals(BigDecimal.valueOf(50.00), resultado.get(0).precoTotal());
        verify(usuarioRepository, times(1)).findByNome("João Silva");
        verify(pedidosRepository, times(1)).findByUsuarioOrderByDataCriacaoDesc(usuario);
    }

    @Test
    void buscarPedidosPorUsuario_QuandoUsuarioNaoExiste_DeveLancarUsernameNotFoundException() {
        // Arrange
        when(usuarioRepository.findByNome(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            perfilService.buscarPedidosPorUsuario("Maria");
        });
        verify(usuarioRepository, times(1)).findByNome("Maria");
        verify(pedidosRepository, never()).findByUsuarioOrderByDataCriacaoDesc(any(Usuarios.class));
    }

    @Test
    void buscarPedidosPorUsuario_QuandoUsuarioNaoTemPedidos_DeveRetornarListaVazia() {
        // Arrange
        when(usuarioRepository.findByNome("João Silva")).thenReturn(Optional.of(usuario));
        when(pedidosRepository.findByUsuarioOrderByDataCriacaoDesc(usuario)).thenReturn(Arrays.asList());

        // Act
        List<PerfilResponseDTO> resultado = perfilService.buscarPedidosPorUsuario("João Silva");

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(usuarioRepository, times(1)).findByNome("João Silva");
        verify(pedidosRepository, times(1)).findByUsuarioOrderByDataCriacaoDesc(usuario);
    }
}
