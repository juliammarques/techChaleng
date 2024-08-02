package com.tech.challenge.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

public record EntidadeBeneficiariaDTO(
        int id,
        @CNPJ(message =  "O Campo deve ser um cnpj")
        @NotBlank(message = "O Campo cnpj deve estar preenchido.")
        String cnpj,
        @NotBlank(message = "O Campo nome deve estar preenchido.")
        String nome,
        @NotBlank(message = "O Campo endereco deve estar preenchido.")
        String endereco,
        @NotBlank(message = "O Campo telefone deve estar preenchido.")
        String telefone,
        @Email(message = "E-mail inválido")
        @NotBlank(message = "O Campo E-mail deve estar preenchido.")
        String email
) {
}
