package com.rlevi.restaurante_backend.service;

import org.springframework.stereotype.Service;

import com.rlevi.restaurante_backend.dto.PerfilResponseDTO;
import com.rlevi.restaurante_backend.model.Pedidos;
import com.rlevi.restaurante_backend.model.Usuarios;
import com.rlevi.restaurante_backend.repository.PedidosRepository;
import com.rlevi.restaurante_backend.repository.UsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class PerfilService {
    @Autowired
    private PedidosRepository pedidosRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<PerfilResponseDTO> buscarPedidosPorUsuario(String nome) {
        Usuarios usuario = usuarioRepository.findByNome(nome).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));

        List<Pedidos> pedidos = pedidosRepository.findByUsuarioOrderByDataCriacaoDesc(usuario);

        return pedidos.stream().map(this::converterParaResponse).collect(Collectors.toList());
    }

    private PerfilResponseDTO converterParaResponse(Pedidos pedido) {
        return PerfilResponseDTO.builder()
                .pedidoId(pedido.getPedidoId())
                .nomeAlimento(pedido.getIdAlimento().getNomeAlimento())
                .quantidade(pedido.getQuantidade())
                .precoUnitario  (pedido.getIdAlimento().getPrecoAlimento())
                .precoTotal(pedido.getPrecoTotal())
                .dataCriacao(pedido.getDataCriacao())
                .build();
    }
}
