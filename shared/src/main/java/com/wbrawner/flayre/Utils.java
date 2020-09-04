package com.wbrawner.flayre;

import java.util.Random;

public final class Utils {
    private static final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String randomId(int length) {
        final var id = new StringBuilder();
        final var random = new Random();
        while (id.length() < length) {
            id.append(characters.charAt(random.nextInt(characters.length())));
        }
        return id.toString();
    }
}
