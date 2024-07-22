package com.tech.challenge.domain.dto;

public record EntidadeBeneficiariaDTO(
        int id,
        String cnpj,
        String nome,
        String endereco,
        String telefone,
        String email
) {
}
