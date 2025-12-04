package com.rlevi.restaurante_backend.shared.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CupomRequestDTO(
    Long idCupom,
    String code,
    BigDecimal percentDesconto,
    BigDecimal fixedDesconto,
    BigDecimal valorMinimo,
    Integer quantidadeUtilizacao,
    LocalDateTime expiraEm,
    Integer usedCount,
    boolean ativo
) {

}
