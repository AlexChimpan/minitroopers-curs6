package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.ScannerType;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domain.TirePosition;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.Map;

@Named("TIRE_SERVICE")
@ApplicationScoped
public class TireService implements MaintenanceType {
    @Override
    public MaintenanceTask create(String vin, TaskType type, String notes, Map<String, Object> additionalData) {
        TirePosition tirePosition = null;
        if(additionalData.get("tirePosition") instanceof String s)
            tirePosition = TirePosition.valueOf(s);
        return MaintenanceTask.createTireService(vin, notes, tirePosition);
    }
}
