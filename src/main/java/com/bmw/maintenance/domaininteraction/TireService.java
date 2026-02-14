package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domain.TirePosition;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.Map;

import static com.bmw.maintenance.domain.MaintenanceTask.createTireService;

@Named("TIRE_SERVICE")
@ApplicationScoped
public class TireService implements MaintenanceTaskType {
    @Override
    public MaintenanceTask create(String vin, TaskType type, String notes, Map<String, Object> additionalData) {
        TirePosition tirePosition = null;

        if(additionalData.get("tirePosition") instanceof String s)
            tirePosition = TirePosition.valueOf(s);

        return createTireService(vin, notes, tirePosition);
    }
}
