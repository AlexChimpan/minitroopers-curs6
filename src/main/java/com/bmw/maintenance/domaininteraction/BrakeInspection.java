package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.Map;

@Named("BRAKE_INSPECTION")
@ApplicationScoped
public class BrakeInspection implements MaintenanceType{
    @Override
    public MaintenanceTask create(String vin, TaskType type, String notes, Map<String, Object> additionalData) {
        return MaintenanceTask.createBrakeInspection(vin, notes);
    }
}
