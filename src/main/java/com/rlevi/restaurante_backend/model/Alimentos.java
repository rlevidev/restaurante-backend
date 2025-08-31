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
@Table(name = "alimentos")
public class Alimentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlimento;
    @Column(name="nome_alimento", nullable = false)
    private String nomeAlimento;
    @Column(name="preco_alimento", nullable = false)
    private Double precoAlimento;
    @Column(name="descricao_alimento")
    private String descricaoAlimento;

}
