package com.bmw.maintenance.domaininteraction.creator;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.ScannerType;
import com.bmw.maintenance.domain.TaskType;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class DiagnosticScanTaskCreator implements MaintenanceTaskCreator {

    @Override
    public TaskType getSupportedType() {
        return TaskType.DIAGNOSTIC_SCAN;
    }

    @Override
    public MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData) {
        List<String> errorCodes = parseErrorCodes(additionalData);
        ScannerType scannerType = requireScannerType(additionalData);
        return MaintenanceTask.createDiagnosticScan(vin, notes, errorCodes, scannerType);
    }

    private List<String> parseErrorCodes(Map<String, Object> additionalData) {
        Object value = additionalData.get("errorCodes");
        if (value == null) {
            return List.of();
        }
        if (value instanceof List<?> list) {
            return list.stream().map(Object::toString).collect(Collectors.toList());
        }
        throw new IllegalArgumentException("errorCodes must be a list of strings");
    }

    private ScannerType requireScannerType(Map<String, Object> additionalData) {
        Object value = additionalData.get("scannerType");
        if (value == null) {
            throw new IllegalArgumentException("scannerType is required for DIAGNOSTIC_SCAN");
        }
        return ScannerType.valueOf(value.toString());
    }
}
