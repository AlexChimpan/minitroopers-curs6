package com.bmw.maintenance.domaininteraction.creation;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.ScannerType;
import com.bmw.maintenance.domain.TaskType;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.jandex.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class DiagnosticScanTaskCreator implements MaintenanceTaskCreator {
    @Override
    public TaskType kindOfTask() {
        return TaskType.DIAGNOSTIC_SCAN;
    }

    @Override
    public MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData) {
        Map<String, Object> data = (additionalData == null) ? Map.of() : additionalData;

        // errorCodes
        Object rawCodes = data.get("errorCodes");
        List<String> errorCodes;
        if(rawCodes == null){
            errorCodes = List.of();
        } else if(rawCodes instanceof List<?> list){
            for(Object obj : list){
                if(!(obj instanceof String)){
                    throw new IllegalArgumentException("error codes should be a string");
                }
            }

            @SuppressWarnings("unchecked")
            List<String> cast = (List<String>) list;
            errorCodes = cast;
        }else if(rawCodes instanceof String s){
            errorCodes = List.of(s);
        } else{
            throw new IllegalArgumentException("error codes");
        }

        // scannerType

        Object rawScanner = data.get("scannerType");
        if (rawScanner == null) {
            throw new IllegalArgumentException("Missing required key: scannerType");
        }
        ScannerType scannerType;
        if (rawScanner instanceof ScannerType st) {
            scannerType = st;
        } else if (rawScanner instanceof String s) {
            try {
                scannerType = ScannerType.valueOf(s.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid 'scannerType': " + s, e);
            }
        } else {
            throw new IllegalArgumentException("Invalid 'scannerType' (String or ScannerType required)");
        }


        return MaintenanceTask.createDiagnosticScan(vin, notes, errorCodes, scannerType);
    }

}
