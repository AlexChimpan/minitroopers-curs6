package com.bmw.maintenance.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

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
@Setter
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

    public MaintenanceTask() {}

    public static MaintenanceTask createOilChange(String vin, String notes) {
        MaintenanceTask task = MaintenanceTask.builder()
                .vin(vin)
                .type(TaskType.OIL_CHANGE)
                .status(TaskStatus.NEW)
                .notes(notes)
                .build();
        return task.validateBusinessRules();
    }

    public static MaintenanceTask createBrakeInspection(String vin, String notes) {
        MaintenanceTask task = MaintenanceTask.builder()
                .vin(vin)
                .type(TaskType.BRAKE_INSPECTION)
                .status(TaskStatus.NEW)
                .notes(notes)
                .build();
        return task.validateBusinessRules();
    }

    /**
     * Creates a new tire service task.
     *
     * @param vin          vehicle identification number
     * @param notes        optional notes for the task
     * @param tirePosition required tire position for this service
     * @return a new {@code MaintenanceTask} configured for tire service
     */
    public static MaintenanceTask createTireService(String vin, String notes, TirePosition tirePosition) {
        MaintenanceTask task = MaintenanceTask.builder()
                .vin(vin)
                .type(TaskType.TIRE_SERVICE)
                .status(TaskStatus.NEW)
                .notes(notes)
                .tirePosition(tirePosition)
                .build();
        return task.validateBusinessRules();
    }

    /**
     * Creates a new diagnostic scan task.
     *
     * @param vin         vehicle identification number
     * @param notes       optional notes for the task
     * @param scannerType required scanner type
     * @param errorCodes  optional list of error codes
     * @return a new {@code MaintenanceTask} configured for diagnostic scan
     */
    public static MaintenanceTask createDiagnosticScan(
            String vin,
            String notes,
            ScannerType scannerType,
            List<String> errorCodes
    ) {
        MaintenanceTask task = MaintenanceTask.builder()
                .vin(vin)
                .type(TaskType.DIAGNOSTIC_SCAN)
                .status(TaskStatus.NEW)
                .notes(notes)
                .scannerType(scannerType)
                .errorCodes(errorCodes)
                .build();
        return task.validateBusinessRules();
    }

    /**
     * Reconstitutes a task from persisted state without applying business rules.
     *
     * @param taskId persisted task identifier
     * @param vin    vehicle identification number
     * @param type   task type
     * @param status task status
     * @param notes        optional notes for the task
     * @param tirePosition tire position for tire services
     * @param scannerType  scanner type for diagnostic scans
     * @param errorCodes   diagnostic error codes, if any
     * @return a {@code MaintenanceTask} populated from stored values
     */
    public static MaintenanceTask reconstitute(
            Long taskId,
            String vin,
            TaskType type,
            TaskStatus status,
            String notes,
            TirePosition tirePosition,
            ScannerType scannerType,
            List<String> errorCodes
    ) {
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

    public void start() {
        if (status != TaskStatus.NEW) {
            throw new IllegalStateException("Only NEW tasks can be started");
        }
        this.status = TaskStatus.IN_PROGRESS;
    }

    public void complete() {
        if (status != TaskStatus.IN_PROGRESS) {
            throw new IllegalStateException("Only IN_PROGRESS tasks can be completed");
        }
        this.status = TaskStatus.DONE;
    }
    private MaintenanceTask validateBusinessRules() {
        Objects.requireNonNull(vin, "VIN must not be null");
        Objects.requireNonNull(type, "Task type must not be null");
        Objects.requireNonNull(status, "Task status must not be null");

        if (type == TaskType.TIRE_SERVICE && tirePosition == null) {
            throw new IllegalStateException("tirePosition is required for TIRE_SERVICE tasks");
        }

        if (type == TaskType.DIAGNOSTIC_SCAN && scannerType == null) {
            throw new IllegalStateException("scannerType is required for DIAGNOSTIC_SCAN tasks");
        }

        return this;
    }
}
