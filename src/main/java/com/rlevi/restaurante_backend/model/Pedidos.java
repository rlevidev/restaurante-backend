package com.rlevi.restaurante_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "pedidos")
public class Pedidos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pedidoId;
    @ManyToOne
    @JoinColumn(name = "alimento_id")
    private Alimentos alimentos;
    @Column(name = "quantidade", nullable=false)
    private Integer quantidade;
    @Column(name = "preco_total")
    private Double precoTotal;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Clientes clientes;

}
