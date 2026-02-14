package com.bmw.maintenance.domaininteraction.creator;


import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domain.details.ScannerType;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Map;

/**
 * TaskCreator implementation responsible for creating diagnostic scan tasks.
 * Supports {@link TaskType#DIAGNOSTIC_SCAN}.
 */
@ApplicationScoped
public class DiagnosticScanCreator implements TaskCreator{
    @Override
    public TaskType supports() {
        return TaskType.DIAGNOSTIC_SCAN;
    }

    @Override
    public MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData) {

        @SuppressWarnings("unchecked")
        List<String> errorCodes = (List<String>) additionalData.get("errorCodes");

        ScannerType scannerType = (ScannerType) additionalData.get("scannerType");

        return MaintenanceTask.createDiagnosticScan(vin, notes, errorCodes, scannerType);

    }
}
