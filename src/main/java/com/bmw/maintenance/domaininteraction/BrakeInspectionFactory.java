package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;

/**
 * Factory for creating brake inspection tasks.
 */
@ApplicationScoped
public class BrakeInspectionFactory implements MaintenanceTaskFactory {

    /**
     * Returns the task type supported by this factory.
     *
     * @return the {@link TaskType} for brake inspection
     */
    @Override
    public TaskType getSupportedType() {
        return TaskType.BRAKE_INSPECTION;
    }

    /**
     * Creates a new brake inspection task.
     *
     * @param vin            the vehicle identification number
     * @param notes          additional notes for the task
     * @param additionalData a map containing type-specific additional data
     * @return a new {@link MaintenanceTask} instance for brake inspection
     */
    @Override
    public MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData) {
        return MaintenanceTask.createBrakeInspection(vin, notes);
    }
}
