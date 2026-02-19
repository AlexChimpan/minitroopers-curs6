package com.bmw.maintenance.domaininteraction.creator;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;

import java.util.Map;

/**
 * Interface for creating a specific type of maintenance task.
 */
public interface MaintenanceTaskCreator {

    /**
     * Returns the task type this creator supports.
     *
     * @return the supported {@link TaskType}
     */
    TaskType getSupportedType();

    /**
     * Creates a maintenance task for the given VIN.
     *
     * @param vin            vehicle identification number
     * @param notes          optional notes
     * @param additionalData type-specific parameters
     * @return the created {@link MaintenanceTask}
     */
    MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData);
}
