package ru.javawebinar.topjava.util;

import org.springframework.validation.BindingResult;

import java.util.StringJoiner;

public class Util {

    private Util() {
    }

    public static <T extends Comparable<? super T>> boolean isBetween(T value, T start, T end) {
        return value.compareTo(start) >= 0 && value.compareTo(end) <= 0;
    }

    public static <T> T orElse(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

    public static String formatBindingResults(BindingResult result) {
        StringJoiner joiner = new StringJoiner("<br>");
        result.getFieldErrors().forEach(
                fe -> {
                    String msg = fe.getDefaultMessage();
                    if (msg != null) {
                        if (!msg.startsWith(fe.getField())) {
                            msg = fe.getField() + ' ' + msg;
                        }
                        joiner.add(msg);
                    }
                });
        return joiner.toString();
    }
}
