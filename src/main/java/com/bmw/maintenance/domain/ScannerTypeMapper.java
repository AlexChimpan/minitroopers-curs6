package com.bmw.maintenance.domain;

import java.util.Optional;

public final class ScannerTypeMapper {
    private ScannerTypeMapper() {}

    public static Optional<ScannerType> fromString(String string) {
        try {
            return Optional.of(ScannerType.valueOf(string.trim().toUpperCase()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
