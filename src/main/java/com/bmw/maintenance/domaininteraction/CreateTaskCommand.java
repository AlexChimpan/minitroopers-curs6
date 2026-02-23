package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.ScannerType;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domain.TirePosition;

import java.util.List;

public record CreateTaskCommand(
        String vin,
        TaskType type,
        String notes,
        TirePosition tirePosition,
        ScannerType scannerType,
        List<String> errorCodes
) {
}

