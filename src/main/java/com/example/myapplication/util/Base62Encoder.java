package com.example.myapplication.util;

public class Base62Encoder {

    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE = 62;

    public static String encode(long value) {
        StringBuilder sb = new StringBuilder();
        if (value == 0) {
            sb.append(BASE62.charAt(0));
        } else {
            while (value > 0) {
                int remainder = (int) (value % BASE);
                sb.append(BASE62.charAt(remainder));
                value /= BASE;
            }
        }
        return sb.reverse().toString();
    }
}

