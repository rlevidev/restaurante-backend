package com.rlevi.restaurante_backend.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Optional;

import com.rlevi.restaurante_backend.shared.dto.request.CupomRequestDTO;
import com.rlevi.restaurante_backend.shared.dto.response.CupomResponseDTO;
import com.rlevi.restaurante_backend.shared.exception.DuplicateResourceException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rlevi.restaurante_backend.domain.entities.Cupom;
import com.rlevi.restaurante_backend.repository.CupomRepository;

@Service
public class CupomService {
    @Autowired
    private CupomRepository cupomRepository;

    public Optional<Cupom> findByCodeIgnoreCase(String code) {
        if(code == null || code.isBlank()) return Optional.empty();
        return cupomRepository.findByCodeIgnoreCase(code.trim());
    }

    /**
     * Valida o cupom para um valor de pedido e retorna uma mensagem de erro se for inválido
     * Se o cupom for válido, retorna null
     */
    public String validateCompra(Cupom cupom, BigDecimal valorPedido) {
        if (cupom == null) return "Cupom inválido";

        if (!cupom.isAtivo()) return "Cupom inativo";
        if (cupom.getExpiraEm() != null && cupom.getExpiraEm().isBefore(LocalDateTime.now())) return "Cupom expirado";
        if (cupom.getQuantidadeUtilizacao() != null && cupom.getUsedCount() != null && cupom.getUsedCount() >= cupom.getQuantidadeUtilizacao()) return "Cupom utilizado";
        if (cupom.getValorMinimo() != null && valorPedido.compareTo(cupom.getValorMinimo()) < 0) return "Cupom incompatível com o valor do pedido";
        return null;
    }

    /**
     * Aplica desconto ao valor do pedido e retorna o valor final
     * Regra: aplica percentual( Se existir ) primeiro, depois desconto fixo ( Se existir )
     */
    public BigDecimal aplicaDesconto(Cupom cupom, BigDecimal valorPedido) {
        if (cupom == null) return valorPedido;
        BigDecimal total = valorPedido;

        // Desconto em porcentagem ( ex: 10% )
        if (cupom.getPercentDesconto() != null) {
            BigDecimal percent = cupom.getPercentDesconto().divide(BigDecimal.valueOf(100), RoundingMode.HALF_EVEN);
            BigDecimal desconto = total.multiply(percent);
            total = total.subtract(desconto);
        }

        // Desconto fixo ( ex: R$ 10 )
        if (cupom.getFixedDesconto() != null) {
            total = total.subtract(cupom.getFixedDesconto());
        }
        
        // Se o total for negativo, retorna zero
        if (total.compareTo(BigDecimal.ZERO) < 0) return BigDecimal.ZERO;

        // Arredonda o total para duas casas
        return total.setScale(2, RoundingMode.HALF_EVEN);
    }

    /**
     * Aplica uso ao contador do cupom utilizados.
     */
    @Transactional
    public void aplicarUsoContador(Cupom cupom) {
        if (cupom.getQuantidadeUtilizacao() == null) cupom.setQuantidadeUtilizacao(0);
        cupom.setQuantidadeUtilizacao(cupom.getQuantidadeUtilizacao() + 1);
        cupomRepository.save(cupom);
    }

    /**
     * Incrementa a lógica de criação de cupom.
     */
    @Transactional
    public CupomResponseDTO criarCupom(@Valid CupomRequestDTO request) {
      // Verificar se o código já existe
      if (cupomRepository.existsByCode(request.code())) {
        throw new DuplicateResourceException("Cupom", "código", request.code());
      }

      Cupom cupom = new Cupom();
      cupom.setCode(request.code());
      cupom.setPercentDesconto(request.percentDesconto());
      cupom.setFixedDesconto(request.fixedDesconto());
      cupom.setValorMinimo(request.valorMinimo());
      cupom.setQuantidadeUtilizacao(request.quantidadeUtilizacao());
      cupom.setExpiraEm(request.expiraEm());
      // Usa um operador ternário para definir o número de usos do código como 0 se for igual a null
      cupom.setUsedCount(request.usedCount() != null ? request.usedCount() : 0);
      cupom.setAtivo(request.ativo());

      // Salva o cupom no banco de dados
      cupom = cupomRepository.save(cupom);

      // Mapear para response ; Determinar se o desconto é percentual ou fixo.
      Double desconto = null;
      String tipoDesconto = null;
      if (cupom.getPercentDesconto() != null) {
        desconto = cupom.getPercentDesconto().doubleValue();
        tipoDesconto = "PERCENTUAL";
      } else if (cupom.getFixedDesconto() != null) {
        desconto = cupom.getFixedDesconto().doubleValue();
        tipoDesconto = "FIXO";
      }

      // Define se o cupom é limitado ou ilimitado("quantidade": null) usando operador ternário
      String tipoQuantidade = cupom.getQuantidadeUtilizacao() != null ? "LIMITADO" : "ILIMITADO";

      // Cria uma resposta para a requisição
      return new CupomResponseDTO(
              cupom.getIdCupom(),
              cupom.getCode(),
              desconto,
              cupom.getQuantidadeUtilizacao(),
              tipoDesconto,
              tipoQuantidade,
              cupom.isAtivo() ? "ATIVO" : "INATIVO"
      );
    }
}
