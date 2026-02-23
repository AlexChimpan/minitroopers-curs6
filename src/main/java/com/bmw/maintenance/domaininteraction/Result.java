package com.bmw.maintenance.domaininteraction;

import java.util.List;

public record Result<T>(boolean success, T value, List<DomainError> errors) {

    public static <T> Result<T> ok(T value) {
        return new Result<>(true, value, List.of());
    }

    public static <T> Result<T> failure(DomainError error) {
        return new Result<>(false, null, List.of(error));
    }

    public static <T> Result<T> failure(List<DomainError> errors) {
        return new Result<>(false, null, List.copyOf(errors));
    }
}

