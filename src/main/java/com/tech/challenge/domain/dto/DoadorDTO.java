package com.tech.challenge.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DoadorDTO(
        int id,
        @NotBlank(message = "O Campo cpfcnpj deve estar preenchido.")
        String cpfcnpj,
        @NotBlank(message = "O Campo nome deve estar preenchido.")
        String nome,
        @NotBlank(message = "O Campo endereço deve estar preenchido.")
        String endereco,
        @NotBlank(message = "O Campo telefone deve estar preenchido.")
        String telefone,
        @Email(message = "E-mail inválido")
        @NotBlank(message = "O Campo E-mail deve estar preenchido.")
        String email
) {
}
