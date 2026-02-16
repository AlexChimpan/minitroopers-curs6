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
    private ScannerType scannerType;
    private List<String> errorCodes;


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
     * Creates a new tire service task in the {@code IN_PROGRESS} status.
     *
     * <p>Initializes a maintenance task for tire service, specifying the tire position.
     * Business rules are validated before returning the instance.</p>
     *
     * @param vin      vehicle identification number
     * @param notes    optional notes for the task
     * @param position the {@link TirePosition} to be serviced; must not be {@code null}
     * @return a new {@code MaintenanceTask} configured for tire service
     * @throws IllegalStateException if required business rules are not met
     */
    public static MaintenanceTask createTireService(String vin, String notes, TirePosition position){
        MaintenanceTask task = MaintenanceTask.builder()
                .vin(vin)
                .type(TaskType.TIRE_SERVICE)
                .status(TaskStatus.IN_PROGRESS)
                .notes(notes)
                .tirePosition(position)
                .build();
        task.validateBusinessRules();
        return task;
    }

    /**
     * Creates a new diagnostic scan task in the {@code IN_PROGRESS} status.
     *
     * <p>Initializes a maintenance task for a diagnostic scan, specifying the scanner type.
     * Business rules are validated before returning the instance.</p>
     *
     * @param vin         vehicle identification number
     * @param notes       optional notes for the task
     * @param scannerType the {@link ScannerType} to be used for the scan; must not be {@code null}
     * @return a new {@code MaintenanceTask} configured for diagnostic scan
     * @throws IllegalStateException if required business rules are not met
     */
    public static MaintenanceTask createDiagnosticScan(String vin, String notes, ScannerType scannerType, List<String> errorCodes){
        MaintenanceTask task = MaintenanceTask.builder()
                .vin(vin)
                .type(TaskType.DIAGNOSTIC_SCAN)
                .status(TaskStatus.IN_PROGRESS)
                .notes(notes)
                .scannerType(scannerType)
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
    public static MaintenanceTask reconstitute(Long taskId, String vin, TaskType type, TaskStatus status, String notes, TirePosition tirePosition, ScannerType scannerType, List<String> errorCodes) {
        return MaintenanceTask.builder()
                .taskId(taskId)
                .vin(vin)
                .type(type)
                .status(status)
                .notes(notes)
                .tirePosition(tirePosition)
                .scannerType(scannerType)
                .errorCodes(errorCodes)
                .build();
    }

    private void validateBusinessRules() {
        if (type == null || status == null) {
            throw new IllegalStateException("Task must have a type and status");
        }
    }
}
