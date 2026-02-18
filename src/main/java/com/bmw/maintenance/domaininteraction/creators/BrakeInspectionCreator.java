package com.bmw.maintenance.domaininteraction.creators;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domaininteraction.MaintenanceTaskCreator;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;

@ApplicationScoped
public class BrakeInspectionCreator implements MaintenanceTaskCreator {

    @Override
    public TaskType supports() {
        return TaskType.BRAKE_INSPECTION;
    }

    @Override
    public MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData) {
        return MaintenanceTask.createBrakeInspection(vin, notes);
    }
}
