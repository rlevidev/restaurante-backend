package com.rlevi.restaurante_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "clientes")
public class Clientes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;
    @Column(name="nome_cliente", nullable = false)
    private String nomeCliente;
    @Column(name="endereco_cliente", nullable = false)
    private String enderecoCliente;
    @Column(name="telefone_cliente", nullable = false)
    private String telefoneCliente;

}
