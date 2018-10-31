package ru.javawebinar.topjava;

public class Util {

    private Util() {}

    public static String getNotFoundMessage(int id) {
        return "Not found entity with id=" + id;
    }
}
