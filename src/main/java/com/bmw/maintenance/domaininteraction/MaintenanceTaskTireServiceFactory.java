package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domain.TirePosition;
import com.bmw.maintenance.domain.TirePositionMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.Map;

@Named("TIRE_SERVICE")
@ApplicationScoped
public class MaintenanceTaskTireServiceFactory implements MaintenanceTaskFactory {
    @Override
    public MaintenanceTask create(String vin, TaskType type, String notes, Map<String, Object> additionalData) {
        TirePosition tirePosition = TirePositionMapper.fromString(additionalData.get("tirePosition").toString()).orElse(null);

        return MaintenanceTask.createTireService(vin, notes, tirePosition);
    }
}
