package com.rlevi.restaurante_backend.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cupoms", uniqueConstraints = @UniqueConstraint(columnNames="code"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cupom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Código que o cliente digita: "RLEVIDEV10"
    @Column(nullable = false, unique = true)
    private String code;

    // Porcentagem de desconto que o cupom concede
    private BigDecimal percentDesconto;

    // Valor fixo de desconto que o cupom concede
    private BigDecimal fixedDesconto;

    // Valor mínimo do pedido para aplicar o cupom
    private BigDecimal valorMinimo;

    // Quantidade de vezes que o cupom pode ser utilizado (null = ilimitado)
    private Integer quantidadeUtilizacao;

    // Data de expiração do cupom
    private LocalDateTime expiraEm;

    // Quantidade de vezes que o cupom foi utilizado
    private Integer usedCount = 0;

    // Indica se o cupom está ativo
    private boolean ativo = true;
    private LocalDateTime dataCriacao = LocalDateTime.now();
}
