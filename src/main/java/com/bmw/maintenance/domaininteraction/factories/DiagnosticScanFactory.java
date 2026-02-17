package com.bmw.maintenance.domaininteraction.factories;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.ScannerType;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domaininteraction.TaskFactory;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class DiagnosticScanFactory implements TaskFactory {
    @Override
    public boolean supports(TaskType type) {
        return type==TaskType.DIAGNOSTIC_SCAN;
    }

    @Override
    public MaintenanceTask create(String vin, String notes, Map<String, Object> parameters) {
        ScannerType scannerType = (ScannerType) parameters.get("scannerType");

        Object errorCodesObj = parameters.get("errorCodes");

        List<String> errorCodes = new ArrayList<>();

        if (errorCodesObj instanceof List<?> rawList) {
            for (Object item : rawList) {
                if (item instanceof String str) {
                    errorCodes.add(str);
                }
            }
        }

        return MaintenanceTask.createDiagnosticScan(vin, notes, scannerType, errorCodes);
    }
}
