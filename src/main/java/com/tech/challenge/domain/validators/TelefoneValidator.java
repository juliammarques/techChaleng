package com.tech.challenge.domain.validators;

public class TelefoneValidator {

    private static final int TELEFONE_MIN_LENGTH = 10; // Ajuste conforme necessário
    private static final int TELEFONE_MAX_LENGTH = 11; // Ajuste conforme necessário

    public static boolean isValidTelefone(String telefone) {
        if (telefone == null || telefone.isEmpty()) {
            return false;
        }

        // Remove caracteres não numéricos
        String cleanedTelefone = telefone.replaceAll("[^0-9]", "");

        // Verifica se o comprimento está dentro do intervalo permitido
        return telefone.length() >= TELEFONE_MIN_LENGTH &&
                telefone.length() <= TELEFONE_MAX_LENGTH &&
                telefone.chars().allMatch(Character::isDigit);
    }
}
