package com.bmw.maintenance.domaininteraction.creation;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domain.TirePosition;

import java.util.Map;

public class TireServiceTaskCreator implements MaintenanceTaskCreator{

    @Override
    public TaskType kindOfTask() {
        return TaskType.TIRE_SERVICE;
    }

    @Override
    public MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData) {
        TirePosition tirePosition = (TirePosition) additionalData.get("tirePosition");
        return MaintenanceTask.createTireService(vin,notes,tirePosition);
    }
}
