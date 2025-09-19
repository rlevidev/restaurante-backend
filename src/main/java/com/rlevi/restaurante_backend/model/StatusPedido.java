package com.rlevi.restaurante_backend.model;

import lombok.Getter;

@Getter
public enum StatusPedido {
    RECEBIDO(),
    NA_FILA(),
    EM_PREPARO(),
    PRONTO(),
    A_CAMINHO(),
    ENTREGUE(),
    CANCELADO();

    // Verifica se o pedido está em andamento
    public boolean isEmAndamento() {
        return this == RECEBIDO ||
                this == NA_FILA ||
                this == EM_PREPARO ||
                this == A_CAMINHO;
    }

    // Verica se o pedido foi finalizado
    public boolean isFinalizado() {
        return this == ENTREGUE || this == CANCELADO;
    }

    // Verifica se o pedido pode ser cancelado
    public boolean podeCancelar() {
        return this == RECEBIDO || this == NA_FILA;
    }

    // Obtém o próximo status natural do fluxo
    public StatusPedido getProximoStatus() {
        switch (this) {
            case RECEBIDO:
                return NA_FILA;
            case NA_FILA:
                return EM_PREPARO;
            case EM_PREPARO:
                return PRONTO;
            case PRONTO:
                return A_CAMINHO;
            case A_CAMINHO:
                return ENTREGUE;
            default:
                return this; // Mantém o mesmo status se for final
        }
    }
}
