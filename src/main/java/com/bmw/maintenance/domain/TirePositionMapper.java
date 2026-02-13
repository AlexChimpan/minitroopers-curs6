package com.bmw.maintenance.domain;

import java.util.Locale;
import java.util.Optional;

public final class TirePositionMapper {
    private TirePositionMapper() {}

    public static Optional<TirePosition> fromString(String value) {
        if (value == null) {
            return Optional.empty();
        }
        try {
            return Optional.of(TirePosition.valueOf(value.trim().toUpperCase(Locale.ROOT)));
        } catch (IllegalArgumentException ex) {
            return Optional.empty();
        }
    }
}
