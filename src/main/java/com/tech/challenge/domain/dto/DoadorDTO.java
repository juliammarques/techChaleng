package com.tech.challenge.domain.dto;

public record DoadorDTO(
        int id,
        String cpfcnpj,
        String nome,
        String endereco,
        String telefone,
        String email
) {
}
