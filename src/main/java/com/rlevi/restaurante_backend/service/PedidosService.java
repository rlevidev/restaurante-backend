package com.rlevi.restaurante_backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rlevi.restaurante_backend.dto.PedidosResponseDTO;
import com.rlevi.restaurante_backend.model.Alimentos;
import com.rlevi.restaurante_backend.model.Pedidos;
import com.rlevi.restaurante_backend.model.Usuarios;
import com.rlevi.restaurante_backend.repository.AlimentosRepository;
import com.rlevi.restaurante_backend.repository.PedidosRepository;

@Service
public class PedidosService {
    @Autowired
    private AlimentosRepository alimentosRepository;
    @Autowired
    private PedidosRepository pedidosRepository;

    public PedidosResponseDTO criarPedido(Long idAlimento, Integer quantidade, String nomeCliente, String enderecoCliente, String telefoneCliente, Usuarios usuario) {
        Alimentos alimento = alimentosRepository.findById(idAlimento).orElseThrow(() -> new RuntimeException("Alimento nao encontrado"));
        Double precoTotal = alimento.getPrecoAlimento() * quantidade;

        Pedidos pedidos = Pedidos.builder()
                .idAlimento(alimento)
                .quantidade(quantidade)
                .precoTotal(precoTotal)
                .nomeCliente(nomeCliente)
                .enderecoCliente(enderecoCliente)
                .telefoneCliente(telefoneCliente)
                .dataCriacao(LocalDateTime.now())
                .usuario(usuario)
                .build();

        Pedidos pedidoSalvo = pedidosRepository.save(pedidos);

        return converterParaDTO(pedidoSalvo);
    }

    private PedidosResponseDTO converterParaDTO(Pedidos pedido) {
        return PedidosResponseDTO.builder()
                .pedidoId(pedido.getPedidoId())
                .nomeAlimento(pedido.getIdAlimento().getNomeAlimento())
                .precoUnitario(pedido.getIdAlimento().getPrecoAlimento())
                .quantidade(pedido.getQuantidade())
                .precoTotal(pedido.getPrecoTotal())
                .nomeCliente(pedido.getNomeCliente())
                .enderecoCliente(pedido.getEnderecoCliente())
                .telefoneCliente(pedido.getTelefoneCliente())
                .dataCriacao(pedido.getDataCriacao())
                .build();
    }

    public List<PedidosResponseDTO> listarPedidos() {
        List<Pedidos> pedidos = pedidosRepository.findAll();
        return pedidos.stream().map(this::converterParaDTO).toList();
    }

    public void deletarPedido(Long id) {
        pedidosRepository.deleteById(id);
    }
}
