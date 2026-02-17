package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domain.TirePosition;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;

@ApplicationScoped
public class TireServiceFactory implements MaintenanceTaskFactory {

    @Override
    public TaskType getSupportedType() {
        return TaskType.TIRE_SERVICE;
    }

    @Override
    public MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData) {
        if(!(additionalData.get("tireCondition") instanceof TirePosition tireCondition)){
            throw new IllegalArgumentException("Invalid tire condition provided");
        }
        return MaintenanceTask.createTireService(vin, notes, tireCondition);
    }
}
