package com.bmw.maintenance.domain;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Map;

/**
 * Creator of diagnostic scan MaintenanceTask
 */
@ApplicationScoped
public class DiagnosticScanMaintenanceTaskCreator implements MaintenanceTaskCreator {
    @Override
    public TaskType taskType() {
        return TaskType.DIAGNOSTIC_SCAN;
    }

    @Override
    public MaintenanceTask createTask(String vin, String notes, Map<String, Object> additionalData) {
        return MaintenanceTask.createDiagnosticScan(vin, (ScannerType) additionalData.get("scannerType"), (List<String>) additionalData.get("errorCodes"), notes);
    }
}
