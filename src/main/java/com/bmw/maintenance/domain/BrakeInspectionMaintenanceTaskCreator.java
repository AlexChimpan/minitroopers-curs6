package com.bmw.maintenance.domain;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;

/**
 * Creator of brake inspection MaintenanceTask
 */
@ApplicationScoped
public class BrakeInspectionMaintenanceTaskCreator implements MaintenanceTaskCreator {

    @Override
    public TaskType taskType() {
        return TaskType.BRAKE_INSPECTION;
    }

    @Override
    public MaintenanceTask createTask(String vin, String notes, Map<String, Object> additionalData) {
        return MaintenanceTask.createBrakeInspection(vin, notes);
    }
}
