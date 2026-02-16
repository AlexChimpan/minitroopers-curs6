package com.bmw.maintenance.domaininteraction.factories;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domaininteraction.TaskFactory;

import java.util.Map;

public class OilChangeFactory implements TaskFactory {
    @Override
    public boolean supports(TaskType type) {
        return type==TaskType.OIL_CHANGE;
    }

    @Override
    public MaintenanceTask create(String vin, String notes, Map<String, Object> parameters) {
        return MaintenanceTask.createOilChange(vin, notes);
    }
}
