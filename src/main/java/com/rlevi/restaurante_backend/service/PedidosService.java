package com.rlevi.restaurante_backend.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rlevi.restaurante_backend.dto.ItemPedidoRequestDTO;
import com.rlevi.restaurante_backend.dto.ItemPedidoResponseDTO;
import com.rlevi.restaurante_backend.dto.PedidosResponseDTO;
import com.rlevi.restaurante_backend.exception.ResourceNotFoundException;
import com.rlevi.restaurante_backend.model.Alimentos;
import com.rlevi.restaurante_backend.model.ItemPedido;
import com.rlevi.restaurante_backend.model.Pedidos;
import com.rlevi.restaurante_backend.model.StatusPedido;
import com.rlevi.restaurante_backend.model.Usuarios;
import com.rlevi.restaurante_backend.repository.AlimentosRepository;
import com.rlevi.restaurante_backend.repository.PedidosRepository;

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
            Alimentos alimento = alimentosRepository.findById(itemRequest.getIdAlimento())
                    .orElseThrow(() -> new ResourceNotFoundException("Alimento", itemRequest.getIdAlimento()));

            BigDecimal subtotal = alimento.getPrecoAlimento()
                    .multiply(BigDecimal.valueOf(itemRequest.getQuantidade()));
            precoTotal = precoTotal.add(subtotal);

            ItemPedido item = ItemPedido.builder()
                    .pedido(pedido)
                    .alimento(alimento)
                    .quantidade(itemRequest.getQuantidade())
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

        return PedidosResponseDTO.builder()
                .pedidoId(pedido.getPedidoId())
                .itens(itensDTO)
                .precoTotal(pedido.getPrecoTotal())
                .nomeCliente(pedido.getNomeCliente())
                .enderecoCliente(pedido.getEnderecoCliente())
                .telefoneCliente(pedido.getTelefoneCliente())
                .dataCriacao(pedido.getDataCriacao())
                .status(pedido.getStatus())
                .build();
    }

    private ItemPedidoResponseDTO converterItemParaDTO(ItemPedido item) {
        return ItemPedidoResponseDTO.builder()
                .idItem(item.getIdItem())
                .idAlimento(item.getAlimento().getIdAlimento())
                .nomeAlimento(item.getAlimento().getNomeAlimento())
                .precoUnitario(item.getPrecoUnitario())
                .quantidade(item.getQuantidade())
                .subtotal(item.getSubtotal())
                .build();
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
