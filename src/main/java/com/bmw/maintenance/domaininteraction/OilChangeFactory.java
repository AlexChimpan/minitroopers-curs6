package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;

/**
 * Factory for creating oil change tasks.
 */
@ApplicationScoped
public class OilChangeFactory implements MaintenanceTaskFactory {

    /**
     * Returns the task type supported by this factory.
     *
     * @return the {@link TaskType} for oil change
     */
    @Override
    public TaskType getSupportedType() {
        return TaskType.OIL_CHANGE;
    }

    /**
     * Creates a new oil change task.
     *
     * @param vin            the vehicle identification number
     * @param notes          additional notes for the task
     * @param additionalData a map containing type-specific additional data
     * @return a new {@link MaintenanceTask} instance for oil change
     */
    @Override
    public MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData) {
        return MaintenanceTask.createOilChange(vin, notes);
    }
}
