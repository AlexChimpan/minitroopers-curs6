package com.bmw.maintenance.domain;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;

/**
 * Creator of tire service MaintenanceTask
 */
@ApplicationScoped
public class TireServiceMaintenanceTaskCreator implements MaintenanceTaskCreator {
    @Override
    public TaskType taskType() {
        return TaskType.TIRE_SERVICE;
    }

    @Override
    public MaintenanceTask createTask(String vin, String notes, Map<String, Object> additionalData) {
        return MaintenanceTask.createTireService(vin, (TirePosition) additionalData.get("tirePosition"), notes);
    }
}
