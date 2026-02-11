package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.ScannerType;
import com.bmw.maintenance.domain.TaskType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.List;
import java.util.Map;

@Named("DIAGNOSTIC_SCAN")
@ApplicationScoped
public class DiagnosticScan implements MaintenanceType {
    @Override
    public MaintenanceTask create(String vin, TaskType type, String notes, Map<String, Object> additionalData) {

        List<String> errorCodes = null;

        if(additionalData.get("errorCodes") instanceof List<?> list){
            errorCodes = list.stream().map(Object::toString).toList();
        }

        ScannerType scannerType = null;
        if(additionalData.get("scannerType") instanceof String s)
            scannerType =  ScannerType.valueOf(s);

        return MaintenanceTask.createDiagnosticScan(vin,
                notes,
                errorCodes==null ? null: errorCodes.toArray(new String[0]),
                scannerType);
    }
}
