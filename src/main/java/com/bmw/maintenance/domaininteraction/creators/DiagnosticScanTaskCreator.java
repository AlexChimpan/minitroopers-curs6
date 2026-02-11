package com.bmw.maintenance.domaininteraction.creators;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.ScannerType;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domaininteraction.MaintenanceTaskCreator;
import com.bmw.maintenance.domaininteraction.TaskCreateKeys;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Map;

@ApplicationScoped
public class DiagnosticScanTaskCreator implements MaintenanceTaskCreator {
    @Override
    public TaskType supportedType(){
        return TaskType.DIAGNOSTIC_SCAN;
    }
    @Override
    @SuppressWarnings("unchecked")
    public MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData){
        ScannerType scannerType = (ScannerType) additionalData.get(TaskCreateKeys.SCANNER_TYPE);
        List<String> errorCodes = (List<String>) additionalData.get(TaskCreateKeys.ERROR_CODES);
        return MaintenanceTask.createDiagnosticScan(vin, scannerType, errorCodes, notes);
    }

}
