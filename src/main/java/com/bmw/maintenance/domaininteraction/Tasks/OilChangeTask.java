package com.bmw.maintenance.domaininteraction.Tasks;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;

@ApplicationScoped
public class OilChangeTask implements TaskCreator{
    @Override
    public TaskType supports() {
        return TaskType.OIL_CHANGE;
    }

    @Override
    public MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData) {
        return MaintenanceTask.createOilChange(vin,notes);
    }
}
