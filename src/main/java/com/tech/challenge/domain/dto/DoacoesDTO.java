package com.tech.challenge.domain.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record DoacoesDTO(
        int id,
        int id_doador,
        int id_itemDisponivelDoacao, //Caso tiver itens
        @Positive(message = "O Campo modalidade deve estar preenchido e deve estar entre as opções " +
                "1-Somente Dinheiro,2-Somente Produtos,3 - Dinheiro e Produtos")
        int modalidade,
        String status,
        @PositiveOrZero(message = "O Campo deve ser positivo ou igual a 0.")
        double valorDoadoReais
) {
}
