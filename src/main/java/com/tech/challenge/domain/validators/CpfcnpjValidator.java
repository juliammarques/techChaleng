package com.tech.challenge.domain.validators;

public class CpfcnpjValidator {
    public static boolean isValidCPF(String cpf) {
        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se o CPF tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais (exemplo: 111.111.111-11)
        if (cpf.chars().distinct().count() == 1) {
            return false;
        }

        // Calcula e valida o primeiro dígito verificador
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int firstDigit = (sum * 10) % 11;
        if (firstDigit == 10) {
            firstDigit = 0;
        }
        if (firstDigit != Character.getNumericValue(cpf.charAt(9))) {
            return false;
        }

        // Calcula e valida o segundo dígito verificador
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int secondDigit = (sum * 10) % 11;
        if (secondDigit == 10) {
            secondDigit = 0;
        }
        return secondDigit == Character.getNumericValue(cpf.charAt(10));
    }

    // Método para validar o CNPJ
    public static boolean isValidCNPJ(String cnpj) {
        // Remove caracteres não numéricos
        cnpj = cnpj.replaceAll("[^0-9]", "");

        // Verifica se o CNPJ tem 14 dígitos
        if (cnpj.length() != 14) {
            return false;
        }

        // Verifica se todos os dígitos são iguais (exemplo: 111.111.111-11)
        if (cnpj.chars().distinct().count() == 1) {
            return false;
        }

        // Calcula e valida o primeiro dígito verificador
        int[] weight1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int sum1 = 0;
        for (int i = 0; i < 12; i++) {
            sum1 += Character.getNumericValue(cnpj.charAt(i)) * weight1[i];
        }
        int firstDigit = (sum1 % 11 < 2) ? 0 : 11 - (sum1 % 11);
        if (firstDigit != Character.getNumericValue(cnpj.charAt(12))) {
            return false;
        }

        // Calcula e valida o segundo dígito verificador
        int[] weight2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int sum2 = 0;
        for (int i = 0; i < 13; i++) {
            sum2 += Character.getNumericValue(cnpj.charAt(i)) * weight2[i];
        }
        int secondDigit = (sum2 % 11 < 2) ? 0 : 11 - (sum2 % 11);
        return secondDigit == Character.getNumericValue(cnpj.charAt(13));
    }
}
