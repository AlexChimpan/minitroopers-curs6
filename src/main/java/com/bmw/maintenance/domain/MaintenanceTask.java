package com.bmw.maintenance.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

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
    private TaskStatus status;
    private String notes;
    private TirePosition tirePosition;
    private List<String> errorCodes;
    private ScannerType scannerType;

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
     * Creates a new tire service task in the IN_PROGRESS status
     * @param vin vehicle identification number
     * @param tirePosition tire that is checked
     * @param notes optional notes for the task
     * @return a new MaintenanceTask configured for tire service
     * @throws IllegalStateException if required business rules are not met
     */
    public static MaintenanceTask createTireService(String vin, TirePosition tirePosition, String notes) {
        MaintenanceTask task = MaintenanceTask.builder()
                .vin(vin)
                .type(TaskType.TIRE_SERVICE)
                .status(TaskStatus.IN_PROGRESS)
                .tirePosition(tirePosition)
                .notes(notes)
                .build();
        task.validateBusinessRules();
        return task;
    }

    /**
     * Creates a new diagnostic scan task in the IN_PROGRESS status
     * @param vin vehicle identification number
     * @param scannerType the type of scanner used
     * @param errorCodes the list of error codes reported by the scanner
     * @param notes optional notes for the task
     * @return a new MaintenanceTask configured for diagnostic scan
     * @throws IllegalStateException if required business rules are not met
     */
    public static MaintenanceTask createDiagnosticScan(String vin, ScannerType scannerType, List<String> errorCodes, String notes) {
        MaintenanceTask task = MaintenanceTask.builder()
                .vin(vin)
                .type(TaskType.DIAGNOSTIC_SCAN)
                .status(TaskStatus.IN_PROGRESS)
                .errorCodes(errorCodes)
                .scannerType(scannerType)
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
    public static MaintenanceTask reconstitute(Long taskId, String vin, TaskType type, TaskStatus status, String notes) {
        return MaintenanceTask.builder()
                .taskId(taskId)
                .vin(vin)
                .type(type)
                .status(status)
                .notes(notes)
                .build();
    }

    private void validateBusinessRules() {
        if (type == null || status == null) {
            throw new IllegalStateException("Task must have a type and status");
        }
        if (type == TaskType.TIRE_SERVICE && tirePosition == null) {
            throw new IllegalStateException("Tire service task must have a tirePosition set");
        }
        if (type == TaskType.DIAGNOSTIC_SCAN && (scannerType == null || errorCodes == null)) {
            throw new IllegalStateException("Diagnostic scan task must have scannerType and errorCodes set");
        }
    }
}
