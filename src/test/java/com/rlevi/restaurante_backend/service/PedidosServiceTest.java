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

import com.rlevi.restaurante_backend.domain.entities.Alimentos;
import com.rlevi.restaurante_backend.domain.entities.ItemPedido;
import com.rlevi.restaurante_backend.domain.entities.Pedidos;
import com.rlevi.restaurante_backend.domain.entities.Usuarios;
import com.rlevi.restaurante_backend.domain.enums.StatusPedido;
import com.rlevi.restaurante_backend.repository.AlimentosRepository;
import com.rlevi.restaurante_backend.repository.PedidosRepository;
import com.rlevi.restaurante_backend.shared.dto.request.ItemPedidoRequestDTO;
import com.rlevi.restaurante_backend.shared.dto.response.PedidosResponseDTO;
import com.rlevi.restaurante_backend.shared.exception.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidosServiceTest {

    @Mock
    private AlimentosRepository alimentosRepository;

    @Mock
    private PedidosRepository pedidosRepository;

    @InjectMocks
    private PedidosService pedidosService;

    private Alimentos alimento;
    private Usuarios usuario;
    private Pedidos pedido;
    private ItemPedidoRequestDTO itemRequest;

    @BeforeEach
    void setUp() {
        alimento = new Alimentos();
        alimento.setIdAlimento(1L);
        alimento.setNomeAlimento("Pizza Margherita");
        alimento.setPrecoAlimento(BigDecimal.valueOf(25.00));

        usuario = new Usuarios();
        usuario.setIdUsuario(1L);
        usuario.setNome("João Silva");

        ItemPedido itemPedido = new ItemPedido();
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

        itemRequest = new ItemPedidoRequestDTO(1L, 2);
    }

    @Test
    void criarPedido_DeveRetornarPedidoResponseDTO() {
        // Arrange
        List<ItemPedidoRequestDTO> itensRequest = Arrays.asList(itemRequest);

        // Create expected pedido with itens
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setIdItem(1L);
        itemPedido.setAlimento(alimento);
        itemPedido.setQuantidade(2);
        itemPedido.setPrecoUnitario(BigDecimal.valueOf(25.00));
        itemPedido.setSubtotal(BigDecimal.valueOf(50.00));

        Pedidos pedidoSalvo = new Pedidos();
        pedidoSalvo.setPedidoId(1L);
        pedidoSalvo.setNomeCliente("João Silva");
        pedidoSalvo.setEnderecoCliente("Rua A, 123");
        pedidoSalvo.setTelefoneCliente("11999999999");
        pedidoSalvo.setUsuario(usuario);
        pedidoSalvo.setStatus(StatusPedido.RECEBIDO);
        pedidoSalvo.setDataCriacao(LocalDateTime.now());
        pedidoSalvo.setPrecoTotal(BigDecimal.valueOf(50.00));
        pedidoSalvo.setItens(Arrays.asList(itemPedido));

        when(alimentosRepository.findById(1L)).thenReturn(Optional.of(alimento));
        when(pedidosRepository.save(any(Pedidos.class))).thenReturn(pedidoSalvo);

        // Act
        PedidosResponseDTO resultado = pedidosService.criarPedido(itensRequest, "João Silva",
                "Rua A, 123", "11999999999", usuario);

        // Assert
        assertNotNull(resultado);
        assertEquals("João Silva", resultado.nomeCliente());
        assertEquals(BigDecimal.valueOf(50.00), resultado.precoTotal()); // 25 * 2
        verify(alimentosRepository, times(1)).findById(1L);
        verify(pedidosRepository, times(1)).save(any(Pedidos.class));
    }

    @Test
    void criarPedido_QuandoAlimentoNaoExiste_DeveLancarResourceNotFoundException() {
        // Arrange
        List<ItemPedidoRequestDTO> itensRequest = Arrays.asList(itemRequest);
        when(alimentosRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            pedidosService.criarPedido(itensRequest, "João Silva", "Rua A, 123", "11999999999", usuario);
        });
        verify(alimentosRepository, times(1)).findById(1L);
        verify(pedidosRepository, never()).save(any(Pedidos.class));
    }

    @Test
    void listarPedidos_DeveRetornarListaDePedidosResponseDTO() {
        // Arrange
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setIdItem(1L);
        itemPedido.setAlimento(alimento);
        itemPedido.setQuantidade(2);
        itemPedido.setPrecoUnitario(BigDecimal.valueOf(25.00));
        itemPedido.setSubtotal(BigDecimal.valueOf(50.00));

        Pedidos pedidoComItens = new Pedidos();
        pedidoComItens.setPedidoId(1L);
        pedidoComItens.setNomeCliente("João Silva");
        pedidoComItens.setEnderecoCliente("Rua A, 123");
        pedidoComItens.setTelefoneCliente("11999999999");
        pedidoComItens.setUsuario(usuario);
        pedidoComItens.setStatus(StatusPedido.RECEBIDO);
        pedidoComItens.setDataCriacao(LocalDateTime.now());
        pedidoComItens.setPrecoTotal(BigDecimal.valueOf(50.00));
        pedidoComItens.setItens(Arrays.asList(itemPedido));

        List<Pedidos> pedidos = Arrays.asList(pedidoComItens);
        when(pedidosRepository.findAll()).thenReturn(pedidos);

        // Act
        List<PedidosResponseDTO> resultado = pedidosService.listarPedidos();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(pedidosRepository, times(1)).findAll();
    }

    @Test
    void deletarPedido_QuandoPedidoExiste_DeveDeletar() {
        // Arrange
        when(pedidosRepository.existsById(1L)).thenReturn(true);

        // Act
        pedidosService.deletarPedido(1L);

        // Assert
        verify(pedidosRepository, times(1)).existsById(1L);
        verify(pedidosRepository, times(1)).deleteById(1L);
    }

    @Test
    void deletarPedido_QuandoPedidoNaoExiste_DeveLancarResourceNotFoundException() {
        // Arrange
        when(pedidosRepository.existsById(anyLong())).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            pedidosService.deletarPedido(1L);
        });
        verify(pedidosRepository, times(1)).existsById(1L);
        verify(pedidosRepository, never()).deleteById(anyLong());
    }

    @Test
    void avancarStatusAutomatico_DeveRetornarProximoStatus() {
        // Arrange
        when(pedidosRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidosRepository.save(any(Pedidos.class))).thenReturn(pedido);

        // Act
        StatusPedido resultado = pedidosService.avancarStatusAutomatico(1L);

        // Assert
        assertEquals(StatusPedido.NA_FILA, resultado);
        verify(pedidosRepository, times(1)).findById(1L);
        verify(pedidosRepository, times(1)).save(pedido);
    }

    @Test
    void avancarStatusAutomatico_QuandoPedidoNaoExiste_DeveLancarResourceNotFoundException() {
        // Arrange
        when(pedidosRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            pedidosService.avancarStatusAutomatico(1L);
        });
        verify(pedidosRepository, times(1)).findById(1L);
        verify(pedidosRepository, never()).save(any(Pedidos.class));
    }

    @Test
    void atualizarStatusManual_DeveRetornarPedidoResponseDTOComNovoStatus() {
        // Arrange
        when(pedidosRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidosRepository.save(any(Pedidos.class))).thenReturn(pedido);

        // Act
        PedidosResponseDTO resultado = pedidosService.atualizarStatusManual(1L, StatusPedido.PRONTO);

        // Assert
        assertNotNull(resultado);
        assertEquals(StatusPedido.PRONTO, resultado.status());
        verify(pedidosRepository, times(1)).findById(1L);
        verify(pedidosRepository, times(1)).save(pedido);
    }

    @Test
    void atualizarStatusManual_QuandoPedidoNaoExiste_DeveLancarResourceNotFoundException() {
        // Arrange
        when(pedidosRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            pedidosService.atualizarStatusManual(1L, StatusPedido.PRONTO);
        });
        verify(pedidosRepository, times(1)).findById(1L);
        verify(pedidosRepository, never()).save(any(Pedidos.class));
    }
}
