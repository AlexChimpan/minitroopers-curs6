package com.bmw.maintenance.domaininteraction.creator;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domain.details.TirePosition;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;

/**
 * TaskCreator implementation responsible for creating tire service tasks.
 * Supports {@link TaskType#TIRE_SERVICE}.
 */
@ApplicationScoped
public class TireServiceTaskCreator implements TaskCreator{
    @Override
    public TaskType supports() {
        return TaskType.TIRE_SERVICE;
    }

    @Override
    public MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData) {


        return MaintenanceTask.createTireService(vin, notes, (TirePosition) additionalData.get("tirePosition"));
    }
}
