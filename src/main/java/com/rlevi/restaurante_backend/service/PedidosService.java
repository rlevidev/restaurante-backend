package com.rlevi.restaurante_backend.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rlevi.restaurante_backend.domain.entities.Alimentos;
import com.rlevi.restaurante_backend.domain.entities.ItemPedido;
import com.rlevi.restaurante_backend.domain.entities.Pedidos;
import com.rlevi.restaurante_backend.domain.entities.Usuarios;
import com.rlevi.restaurante_backend.domain.enums.StatusPedido;
import com.rlevi.restaurante_backend.repository.AlimentosRepository;
import com.rlevi.restaurante_backend.repository.PedidosRepository;
import com.rlevi.restaurante_backend.shared.dto.request.ItemPedidoRequestDTO;
import com.rlevi.restaurante_backend.shared.dto.response.ItemPedidoResponseDTO;
import com.rlevi.restaurante_backend.shared.dto.response.PedidosResponseDTO;
import com.rlevi.restaurante_backend.shared.exception.ResourceNotFoundException;

@Service
public class PedidosService {
    @Autowired
    private AlimentosRepository alimentosRepository;
    @Autowired
    private PedidosRepository pedidosRepository;

    @Transactional
    public PedidosResponseDTO criarPedido(List<ItemPedidoRequestDTO> itensRequest, String nomeCliente,
            String enderecoCliente, String telefoneCliente, Usuarios usuario) {

        // Criar o pedido
        Pedidos pedido = Pedidos.builder()
                .nomeCliente(nomeCliente)
                .enderecoCliente(enderecoCliente)
                .telefoneCliente(telefoneCliente)
                .usuario(usuario)
                .build();

        // Processar itens e calcular total
        List<ItemPedido> itens = new ArrayList<>();
        BigDecimal precoTotal = BigDecimal.ZERO;

        for (ItemPedidoRequestDTO itemRequest : itensRequest) {
            Alimentos alimento = alimentosRepository.findById(itemRequest.idAlimento())
                    .orElseThrow(() -> new ResourceNotFoundException("Alimento", itemRequest.idAlimento()));

            BigDecimal subtotal = alimento.getPrecoAlimento()
                    .multiply(BigDecimal.valueOf(itemRequest.quantidade()));
            precoTotal = precoTotal.add(subtotal);

            ItemPedido item = ItemPedido.builder()
                    .pedido(pedido)
                    .alimento(alimento)
                    .quantidade(itemRequest.quantidade())
                    .precoUnitario(alimento.getPrecoAlimento())
                    .subtotal(subtotal)
                    .build();

            itens.add(item);
        }

        pedido.setItens(itens);
        pedido.setPrecoTotal(precoTotal);

        Pedidos pedidoSalvo = pedidosRepository.save(pedido);
        return converterParaDTO(pedidoSalvo);
    }

    private PedidosResponseDTO converterParaDTO(Pedidos pedido) {
        List<ItemPedidoResponseDTO> itensDTO = pedido.getItens().stream()
                .map(this::converterItemParaDTO)
                .toList();

        return new PedidosResponseDTO(
                pedido.getPedidoId(),
                itensDTO,
                pedido.getPrecoTotal(),
                pedido.getNomeCliente(),
                pedido.getEnderecoCliente(),
                pedido.getTelefoneCliente(),
                pedido.getDataCriacao(),
                pedido.getStatus());
    }

    private ItemPedidoResponseDTO converterItemParaDTO(ItemPedido item) {
        return new ItemPedidoResponseDTO(
                item.getIdItem(),
                item.getAlimento().getIdAlimento(),
                item.getAlimento().getNomeAlimento(),
                item.getPrecoUnitario(),
                item.getQuantidade(),
                item.getSubtotal());
    }

    public List<PedidosResponseDTO> listarPedidos() {
        List<Pedidos> pedidos = pedidosRepository.findAll();
        return pedidos.stream().map(this::converterParaDTO).toList();
    }

    public void deletarPedido(Long id) {
        if (!pedidosRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pedido", id);
        }
        pedidosRepository.deleteById(id);
    }

    public StatusPedido avancarStatusAutomatico(Long pedidoId) {
        Pedidos pedido = pedidosRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido", pedidoId));

        StatusPedido proximoStatus = pedido.getStatus().getProximoStatus();
        pedido.setStatus(proximoStatus);

        return pedidosRepository.save(pedido).getStatus();
    }

    public PedidosResponseDTO atualizarStatusManual(Long pedidoId, StatusPedido novoStatus) {
        Pedidos pedido = pedidosRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido", pedidoId));

        pedido.setStatus(novoStatus);
        pedido.setDataAtualizacao(LocalDateTime.now());

        Pedidos atualizado = pedidosRepository.save(pedido);
        return converterParaDTO(atualizado);
    }
}
