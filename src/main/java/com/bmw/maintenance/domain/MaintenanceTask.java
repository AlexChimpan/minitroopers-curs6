package com.bmw.maintenance.domain;

import com.bmw.maintenance.domain.DiagnosticScan.ErrorCodes;
import com.bmw.maintenance.domain.DiagnosticScan.ScannerType;
import com.bmw.maintenance.domain.TireTask.TirePosition;
import com.bmw.maintenance.domain.TireTask.TireServiceType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

/**
 * Domain entity representing a maintenance task for a vehicle.
 * <p>
 * This class follows Domain\-Driven Design principles and encapsulates business logic
 * related to vehicle maintenance tasks. Instances should be created using the provided
 * factory methods rather than the builder directly.
 * </p>
 *
 * @see TaskType for available task types
 * @see TaskStatus for possible task statuses
 */
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MaintenanceTask {

    private Long taskId;
    private String vin;
    private TaskType type;
    private TirePosition tirePosition;
    private TireServiceType tireServiceType;
    private ScannerType scannerType;
    private Set<ErrorCodes> errorCodes;
    private TaskStatus status;
    private String notes;


    /**
     * Creates a new oil change task in the \`IN\_PROGRESS\` status.
     *
     * @param vin   vehicle identification number
     * @param notes optional notes for the task
     * @return a new \`MaintenanceTask\` configured for oil change
     * @throws IllegalStateException if required business rules are not met
     */
    public static MaintenanceTask createOilChange(String vin, String notes) {
        MaintenanceTask task = MaintenanceTask.builder()
                .vin(vin)
                .type(TaskType.OIL_CHANGE)
                .status(TaskStatus.IN_PROGRESS)
                .notes(notes)
                .build();
        task.validateBusinessRules();
        return task;
    }

    public static MaintenanceTask createTireService(String vin, String notes, TireServiceType type, TirePosition position) {
        MaintenanceTask task = MaintenanceTask.builder()
                .vin(vin)
                .type(TaskType.TIRE_SERVICE)
                .status(TaskStatus.IN_PROGRESS)
                .tireServiceType(TireServiceType.valueOf(String.valueOf(type)))
                .tirePosition(TirePosition.valueOf(String.valueOf(position)))
                .notes(notes)
                .build();
        task.validateBusinessRules();
        return task;
    }

    public static MaintenanceTask createDiagnosticScanService(String vin, String notes, ScannerType scannerType, Set<ErrorCodes> errorCodes){
        MaintenanceTask task = MaintenanceTask.builder()
                .vin(vin)
                .type(TaskType.DIAGNOSTIC_SCAN)
                .status(TaskStatus.IN_PROGRESS)
                .scannerType(ScannerType.valueOf(String.valueOf(scannerType)))
                .errorCodes(errorCodes)
                .notes(notes)
                .build();
        task.validateBusinessRules();
        return task;
    }

    /**
     * Creates a new brake inspection task in the \`IN\_PROGRESS\` status.
     *
     * @param vin   vehicle identification number
     * @param notes optional notes for the task
     * @return a new \`MaintenanceTask\` configured for brake inspection
     * @throws IllegalStateException if required business rules are not met
     */
    public static MaintenanceTask createBrakeInspection(String vin, String notes) {
        MaintenanceTask task = MaintenanceTask.builder()
                .vin(vin)
                .type(TaskType.BRAKE_INSPECTION)
                .status(TaskStatus.IN_PROGRESS)
                .notes(notes)
                .build();
        task.validateBusinessRules();
        return task;
    }

    /**
     * Reconstitutes a task from persisted state without applying business rules.
     *
     * @param taskId persisted task identifier
     * @param vin    vehicle identification number
     * @param type   task type
     * @param status task status
     * @param notes  optional notes for the task
     * @return a \`MaintenanceTask\` populated from stored values
     */
    public static MaintenanceTask reconstitute(Long taskId, String vin, TaskType type, TaskStatus status, TirePosition tirePosition, TireServiceType tireServiceType,ScannerType scannerType,Set<ErrorCodes> errorCodes, String notes) {
        return MaintenanceTask.builder()
                .taskId(taskId)
                .vin(vin)
                .type(type)
                .status(status)
                .tireServiceType(tireServiceType)
                .tirePosition(tirePosition)
                .scannerType(scannerType)
                .errorCodes(errorCodes)
                .notes(notes)
                .build();
    }

    private void validateBusinessRules() {
        if (type == null || status == null) {
            throw new IllegalStateException("Task must have a type and status");
        }
    }
}
