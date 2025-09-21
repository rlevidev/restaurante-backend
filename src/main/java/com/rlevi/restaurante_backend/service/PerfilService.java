package com.rlevi.restaurante_backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rlevi.restaurante_backend.dto.ItemPedidoResponseDTO;
import com.rlevi.restaurante_backend.dto.PerfilResponseDTO;
import com.rlevi.restaurante_backend.model.ItemPedido;
import com.rlevi.restaurante_backend.model.Pedidos;
import com.rlevi.restaurante_backend.model.Usuarios;
import com.rlevi.restaurante_backend.repository.PedidosRepository;
import com.rlevi.restaurante_backend.repository.UsuarioRepository;

@Service
public class PerfilService {

    @Autowired
    private PedidosRepository pedidosRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<PerfilResponseDTO> buscarPedidosPorUsuario(String nome) {
        Usuarios usuario = usuarioRepository.findByNome(nome)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));

        List<Pedidos> pedidos = pedidosRepository.findByUsuarioOrderByDataCriacaoDesc(usuario);

        return pedidos.stream().map(this::converterParaResponse).collect(Collectors.toList());
    }

    private PerfilResponseDTO converterParaResponse(Pedidos pedido) {
        List<ItemPedidoResponseDTO> itensDTO = pedido.getItens().stream()
                .map(this::converterItemParaDTO)
                .collect(Collectors.toList());

        return PerfilResponseDTO.builder()
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
}
