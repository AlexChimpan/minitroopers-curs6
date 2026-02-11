package com.bmw.maintenance.domain;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;

/**
 * Creator of oil change MaintenanceTask
 */
@ApplicationScoped
public class OilChangeMaintenanceTaskCreator implements MaintenanceTaskCreator {
    @Override
    public TaskType taskType() {
        return TaskType.OIL_CHANGE;
    }

    @Override
    public MaintenanceTask createTask(String vin, String notes, Map<String, Object> additionalData) {
        return MaintenanceTask.createOilChange(vin, notes);
    }
}
