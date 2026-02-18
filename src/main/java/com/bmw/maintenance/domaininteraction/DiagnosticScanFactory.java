package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.ScannerType;
import com.bmw.maintenance.domain.TaskType;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Map;

/**
 * Factory for creating diagnostic scan tasks.
 */
@ApplicationScoped
public class DiagnosticScanFactory implements MaintenanceTaskFactory {

    /**
     * Returns the task type supported by this factory.
     *
     * @return the {@link TaskType} for diagnostic scan
     */
    @Override
    public TaskType getSupportedType() {
        return TaskType.DIAGNOSTIC_SCAN;
    }

    /**
     * Creates a new diagnostic scan task.
     *
     * @param vin            the vehicle identification number
     * @param notes          additional notes for the task
     * @param additionalData a map containing type-specific additional data
     * @return a new {@link MaintenanceTask} instance for diagnostic scan
     * @throws IllegalArgumentException if the scanner type is invalid
     */
    @Override
    public MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData) {
        if (!(additionalData.get("scannerType") instanceof ScannerType scannerType)) {
            throw new IllegalArgumentException("Invalid scanner type provided");
        }
        List<String> errorCodes = (List<String>) additionalData.get("errorCodes");
        return MaintenanceTask.createDiagnosticScan(vin, notes, scannerType, errorCodes);
    }
}
