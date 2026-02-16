package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.ScannerType;
import com.bmw.maintenance.domain.TaskType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.BadRequestException;

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

        var scannerType = (ScannerType) additionalData.get(SCANNER_TYPE);
        var errorCodes = (List<String>) additionalData.get(ERROR_CODES);

        if (scannerType == null) throw new BadRequestException("scannerType is required for DIAGNOSTIC_SCAN");
        if (errorCodes == null || errorCodes.isEmpty()) throw new BadRequestException("errorCodes is required for DIAGNOSTIC_SCAN");

        return MaintenanceTask.createDiagnosticScan(vin, notes, errorCodes, scannerType);
    }
}
