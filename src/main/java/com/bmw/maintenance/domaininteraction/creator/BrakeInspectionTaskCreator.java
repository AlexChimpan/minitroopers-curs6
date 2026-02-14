package com.bmw.maintenance.domaininteraction.creator;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;


/**
 * TaskCreator implementation responsible for creating brake inspection tasks.
 * Supports {@link TaskType#BRAKE_INSPECTION}.
 */
@ApplicationScoped
public class BrakeInspectionTaskCreator implements TaskCreator{

    @Override
    public TaskType supports() {
        return TaskType.BRAKE_INSPECTION;
    }

    @Override
    public MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData) {
        return MaintenanceTask.createBrakeInspection(vin, notes);
    }
}
