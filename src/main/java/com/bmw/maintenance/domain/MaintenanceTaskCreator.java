package com.bmw.maintenance.domain;

import java.util.Map;

/**
 * Defines creation of tasks
 */
public interface MaintenanceTaskCreator {
    /**
     * Get the type of task the creator produces
     * @return a variant of enum {@link TaskType}
     */
    TaskType taskType();

    /**
     * Creates a MaintenanceTask with business rules validation
     * @param vin vehicle identification data
     * @param notes optional notes
     * @param additionalData other necessary data depending on the type of task
     * @return a new MaintenanceTask
     */
    MaintenanceTask createTask(String vin, String notes, Map<String, Object> additionalData);
}
