package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.ScannerType;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domain.TirePosition;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@ApplicationScoped
@Named("DIAGNOSTIC_SCAN")
public class DiagnosticScan implements MaintenanceTaskTypes{

    /**
     * Creates a new diagnostic scan task in the \`IN\_PROGRESS\` status.
     *
     * @param vin   vehicle identification number
     * @param notes optional notes for the task
     * @param additionalData -> error codes for the task + scanner type for the task
     * @return a new \`MaintenanceTask\` configured for brake inspection
     * @throws IllegalStateException if required business rules are not met
     */

    @Override
    public MaintenanceTask createTask(String vin, TaskType type, String notes, Map<String, Object> additionalData) {
        {
            var errorCodes = additionalData.get("errorCodes");
            List<String> ec = (List<String>) errorCodes ;


            var scannerType = additionalData.get("scannerType");
            ScannerType sc = null;

            if (scannerType instanceof String s) {
                sc = ScannerType.valueOf(s);
            }
            return MaintenanceTask.createDiagnosticScan(vin, notes, ec, sc);
        }
    }
}
