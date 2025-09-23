package com.rlevi.restaurante_backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rlevi.restaurante_backend.domain.entities.ItemPedido;
import com.rlevi.restaurante_backend.domain.entities.Pedidos;
import com.rlevi.restaurante_backend.domain.entities.Usuarios;
import com.rlevi.restaurante_backend.repository.PedidosRepository;
import com.rlevi.restaurante_backend.repository.UsuarioRepository;
import com.rlevi.restaurante_backend.shared.dto.response.ItemPedidoResponseDTO;
import com.rlevi.restaurante_backend.shared.dto.response.PerfilResponseDTO;

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

        return new PerfilResponseDTO(
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
}
