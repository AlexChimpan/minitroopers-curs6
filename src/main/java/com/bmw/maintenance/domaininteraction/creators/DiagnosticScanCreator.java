package com.bmw.maintenance.domaininteraction.creators;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.ScannerType;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domaininteraction.CreateTaskCommand;
import com.bmw.maintenance.domaininteraction.MaintenanceTaskCreator;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class DiagnosticScanCreator implements MaintenanceTaskCreator {

    @Override
    public TaskType supports() {
        return TaskType.DIAGNOSTIC_SCAN;
    }

    @Override
    public MaintenanceTask create(CreateTaskCommand command) {
        ScannerType scannerType = command.scannerType();
        if (scannerType == null) {
            throw new IllegalArgumentException("scannerType is required for DIAGNOSTIC_SCAN");
        }

        List<String> errorCodes = command.errorCodes() != null ? command.errorCodes() : List.of();

        return MaintenanceTask.createDiagnosticScan(command.vin(), command.notes(), scannerType, errorCodes);
    }
}
