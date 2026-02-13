package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.ScannerType;
import com.bmw.maintenance.domain.ScannerTypeMapper;
import com.bmw.maintenance.domain.TaskType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.List;
import java.util.Map;

@Named("DIAGNOSTIC_SCAN")
@ApplicationScoped
public class MaintenanceTaskDiagnosticScanFactory implements MaintenanceTaskFactory {
    @Override
    public MaintenanceTask create(String vin, TaskType type, String notes, Map<String, Object> additionalData) {
        List<String> errorCodes = (List<String>) additionalData.get("errorCodes");
        ScannerType scannerType = ScannerTypeMapper.fromString(additionalData.get("scannerType").toString()).orElse(null);

        return MaintenanceTask.createDiagnosticScan(vin, notes, errorCodes, scannerType);
    }
}
