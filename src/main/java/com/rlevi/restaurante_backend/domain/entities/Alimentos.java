package com.rlevi.restaurante_backend.domain.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @Column(name = "nome_alimento", nullable = false)
    @NotBlank(message = "Nome do alimento é obrigatório")
    @Size(min = 2, max = 100, message = "Nome do alimento deve ter entre 2 e 100 caracteres")
    private String nomeAlimento;

    @Column(name = "preco_alimento", nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Preço do alimento é obrigatório")
    @DecimalMin(value = "0.01", message = "Preço do alimento deve ser maior que zero")
    @Digits(integer = 8, fraction = 2, message = "Preço deve ter no máximo 8 dígitos inteiros e 2 decimais")
    private BigDecimal precoAlimento;

    @Column(name = "descricao_alimento")
    private String descricaoAlimento;

}
