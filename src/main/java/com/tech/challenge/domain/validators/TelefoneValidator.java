package com.tech.challenge.domain.validators;

public class TelefoneValidator {
    public static boolean isValidTelefone(String telefone) {
        return telefone.chars().allMatch(Character::isDigit);
    }
}
