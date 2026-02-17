package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.ScannerType;
import com.bmw.maintenance.domain.TaskType;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Map;

@ApplicationScoped
public class DiagnosticScanFactory implements MaintenanceTaskFactory {

    @Override
    public TaskType getSupportedType() {
        return TaskType.DIAGNOSTIC_SCAN;
    }

    @Override
    public MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData) {
        if(!(additionalData.get("scannerType") instanceof ScannerType scannerType)){
            throw new IllegalArgumentException("Invalid scanner type provided");
        }
        List<String> errorCodes = (List<String>) additionalData.get("errorCodes");
        return MaintenanceTask.createDiagnosticScan(vin, notes, scannerType, errorCodes);
    }
}
