package com.bmw.maintenance.domain.creators;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.ScannerType;
import com.bmw.maintenance.domain.TaskType;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Map;

@ApplicationScoped
public class DiagnosticScanTaskCreator implements MaintenanceTaskCreator {
    @Override
    public TaskType supports() {
        return TaskType.DIAGNOSTIC_SCAN;
    }

    @Override
    public MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData) {
        String scannerTypeStr = (String) additionalData.get("scannerType");
        if (scannerTypeStr == null) {
            throw new IllegalArgumentException("Scanner type is required");
        }

        ScannerType scannerType;
        try {
            scannerType = ScannerType.valueOf(scannerTypeStr);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid scanner type: " + scannerTypeStr, e);
        }

        List<String> errorCodes = (List<String>) additionalData.get("errorCodes");
        return MaintenanceTask.createDiagnosticScan(vin,notes,scannerType,errorCodes);
    }
}
