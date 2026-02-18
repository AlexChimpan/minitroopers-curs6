package com.bmw.maintenance.domaininteraction.creators;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.ScannerType;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domaininteraction.MaintenanceTaskCreator;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Map;

@ApplicationScoped
public class DiagnosticScanCreator implements MaintenanceTaskCreator {

    @Override
    public TaskType supports() {
        return TaskType.DIAGNOSTIC_SCAN;
    }

    @SuppressWarnings("unchecked")
    @Override
    public MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData) {
        Object st = additionalData.get("scannerType");
        if (st == null) {
            throw new IllegalArgumentException("scannerType is required for DIAGNOSTIC_SCAN");
        }
        ScannerType scannerType = ScannerType.valueOf(st.toString());

        List<String> errorCodes = (List<String>) additionalData.getOrDefault("errorCodes", List.of());

        return MaintenanceTask.createDiagnosticScan(vin, notes, scannerType, errorCodes);
    }
}
