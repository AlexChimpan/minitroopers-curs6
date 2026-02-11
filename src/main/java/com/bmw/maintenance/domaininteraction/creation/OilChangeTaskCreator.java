package com.bmw.maintenance.domaininteraction.creation;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;

import java.util.Map;

@ApplicationScoped
public class OilChangeTaskCreator implements MaintenanceTaskCreator{
    @Override
    public TaskType kindOfTask() {
        return TaskType.OIL_CHANGE;
    }

    @Override
    public MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData) {
        return MaintenanceTask.createOilChange(vin, notes);
    }
}
