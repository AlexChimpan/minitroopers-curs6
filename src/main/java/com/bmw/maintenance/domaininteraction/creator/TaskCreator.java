package com.bmw.maintenance.domaininteraction.creator;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;

import java.util.Map;

/**
 * Defines the contract for creating maintenance tasks based on a specific {@link TaskType}.
 */
public interface TaskCreator {

    /**
     * Returns the task type supported by this creator.
     *
     * @return the supported {@link TaskType}
     */
    TaskType supports();

    /**
     * Creates a new {@link MaintenanceTask} using the given parameters and any type-specific data.
     *
     * @param vin             vehicle identification number
     * @param notes           optional task notes
     * @param additionalData  extra fields required for certain task types
     * @return a newly constructed {@link MaintenanceTask}
     */
    MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData);
}
