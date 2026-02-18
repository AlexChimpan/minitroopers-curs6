package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domain.TirePosition;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;

/**
 * Factory for creating tire service tasks.
 */
@ApplicationScoped
public class TireServiceFactory implements MaintenanceTaskFactory {

    /**
     * Returns the task type supported by this factory.
     *
     * @return the {@link TaskType} for tire service
     */
    @Override
    public TaskType getSupportedType() {
        return TaskType.TIRE_SERVICE;
    }

    /**
     * Creates a new tire service task.
     *
     * @param vin            the vehicle identification number
     * @param notes          additional notes for the task
     * @param additionalData a map containing type-specific additional data
     * @return a new {@link MaintenanceTask} instance for tire service
     * @throws IllegalArgumentException if the tire condition is invalid
     */
    @Override
    public MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData) {
        if (!(additionalData.get("tireCondition") instanceof TirePosition tireCondition)) {
            throw new IllegalArgumentException("Invalid tire condition provided");
        }
        return MaintenanceTask.createTireService(vin, notes, tireCondition);
    }
}
