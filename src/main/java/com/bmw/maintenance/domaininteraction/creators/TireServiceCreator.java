package com.bmw.maintenance.domaininteraction.creators;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domain.TirePosition;
import com.bmw.maintenance.domaininteraction.MaintenanceTaskCreator;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;

@ApplicationScoped
public class TireServiceCreator implements MaintenanceTaskCreator {

    @Override
    public TaskType supports() {
        return TaskType.TIRE_SERVICE;
    }

    @Override
    public MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData) {
        Object raw = additionalData.get("tirePosition");
        if (raw == null) {
            throw new IllegalArgumentException("tirePosition is required for TIRE_SERVICE");
        }

        TirePosition pos = TirePosition.valueOf(raw.toString());

        return MaintenanceTask.createTireService(vin, notes, pos);
    }
}
