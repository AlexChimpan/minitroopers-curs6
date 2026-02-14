package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.ScannerType;
import com.bmw.maintenance.domain.TirePosition;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.List;

import static com.bmw.maintenance.domain.MaintenanceTask.createDiagnosticScan;

@Named("DIAGNOSTIC_SCAN")
@ApplicationScoped
public class DiagnosticScan implements MaintenanceTaskType {
    @Override
    public com.bmw.maintenance.domain.MaintenanceTask create(String vin, com.bmw.maintenance.domain.TaskType type, String notes, java.util.Map<String, Object> additionalData) {
        List<String> errorCodes = null;

        if(additionalData.get("errorCodes") instanceof List<?> list)
            errorCodes = list.stream()
                    .map(Object::toString)
                    .toList();

        ScannerType scannerType = null;

        if(additionalData.get("scannerType") instanceof String s)
            scannerType = ScannerType.valueOf(s);

        return createDiagnosticScan(vin, notes, errorCodes, scannerType);
    }
}
