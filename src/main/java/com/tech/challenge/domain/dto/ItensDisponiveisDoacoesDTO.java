package com.tech.challenge.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
public record ItensDisponiveisDoacoesDTO(
        int id,
        @Positive(message = "O Id da entidade beneficiaria deve ser maior que 0")
        int id_entidade,
        @NotBlank(message = "O Campo descricao deve estar preenchido.")
        String descricao,
        @Positive(message = "A Quantidade deve ser maior que 0")
        Double quantidade,
        String status,
        @NotBlank(message = "O Campo medida deve estar preenchido.")
        String medida
) {
}
