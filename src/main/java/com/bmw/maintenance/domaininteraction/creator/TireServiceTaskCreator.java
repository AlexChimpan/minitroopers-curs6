package com.bmw.maintenance.domaininteraction.creator;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domain.TirePosition;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;

@ApplicationScoped
public class TireServiceTaskCreator implements MaintenanceTaskCreator {

    @Override
    public TaskType getSupportedType() {
        return TaskType.TIRE_SERVICE;
    }

    @Override
    public MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData) {
        TirePosition tirePosition = requireTirePosition(additionalData);
        return MaintenanceTask.createTireService(vin, notes, tirePosition);
    }

    private TirePosition requireTirePosition(Map<String, Object> additionalData) {
        Object value = additionalData.get("tirePosition");
        if (value == null) {
            throw new IllegalArgumentException("tirePosition is required for TIRE_SERVICE");
        }
        return TirePosition.valueOf(value.toString());
    }
}
