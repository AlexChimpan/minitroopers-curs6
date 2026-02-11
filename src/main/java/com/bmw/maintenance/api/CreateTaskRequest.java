package com.bmw.maintenance.api;

import com.bmw.maintenance.domain.ScannerType;
import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domain.TirePosition;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;


public record CreateTaskRequest(
        @NotBlank String vin,
        @NotNull TaskType type,
        String notes,
        TirePosition tirePosition,
        ScannerType scannerType,
        List<String> errorCodes
) {
}
