package com.tech.challenge.domain.formatter;

public class TelefoneFormatter {
    public static String extractNumbers(String input) {
        return input.replaceAll("[^0-9]", "");
    }
}
