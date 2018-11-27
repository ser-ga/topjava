package ru.javawebinar.topjava.util.formater;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class LDTFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<LDTFormat> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<>(List.of(LocalDate.class, LocalTime.class));
    }

    @Override
    public Printer<?> getPrinter(LDTFormat annotation, Class<?> fieldType) {
        if (fieldType.equals(LocalTime.class)) return new LocalTimeFormatter();
        return new LocalDateFormatter();
    }

    @Override
    public Parser<?> getParser(LDTFormat annotation, Class<?> fieldType) {
        if (fieldType.equals(LocalTime.class)) return new LocalTimeFormatter();
        return new LocalDateFormatter();
    }
}
