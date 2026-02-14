package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.Map;

import static com.bmw.maintenance.domain.MaintenanceTask.createOilChange;

@Named("OIL_CHANGE")
@ApplicationScoped
public class OilChange implements MaintenanceTaskType {
    @Override
    public MaintenanceTask create(String vin, TaskType type, String notes, Map<String, Object> additionalData) {
        return createOilChange(vin, notes);
    }
}
