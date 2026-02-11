package com.bmw.maintenance.domain.creators;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domain.TirePosition;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;

@ApplicationScoped
public class TireServiceTaskCreator implements MaintenanceTaskCreator {
    @Override
    public TaskType supports() {
        return TaskType.TIRE_SERVICE;
    }

    @Override
    public MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData) {
        Object positionObj = additionalData.get("tirePosition");

        if(positionObj == null)
        {
            throw new IllegalArgumentException("Tire position is required");
        }

        TirePosition tirePosition=TirePosition.valueOf(((String) positionObj).toUpperCase());

        return MaintenanceTask.createTireService(vin,notes,tirePosition);
    }
}
