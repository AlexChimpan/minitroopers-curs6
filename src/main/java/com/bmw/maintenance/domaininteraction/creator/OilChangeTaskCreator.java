package com.bmw.maintenance.domaininteraction.creator;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;

/**
 * TaskCreator implementation responsible for creating oil change tasks.
 * Supports {@link TaskType#OIL_CHANGE}.
 */
@ApplicationScoped
public class OilChangeTaskCreator implements TaskCreator{
    @Override
    public TaskType supports() {
        return TaskType.OIL_CHANGE;
    }

    @Override
    public MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData) {
        return MaintenanceTask.createOilChange(vin, notes);
    }
}
