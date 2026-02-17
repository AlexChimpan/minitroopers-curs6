package com.bmw.maintenance.domaininteraction.factories;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domain.TirePosition;
import com.bmw.maintenance.domaininteraction.TaskFactory;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;

@ApplicationScoped
public class TireServiceFactory implements TaskFactory {
    @Override
    public boolean supports(TaskType type) {
        return type==TaskType.TIRE_SERVICE;
    }

    @Override
    public MaintenanceTask create(String vin, String notes, Map<String, Object> parameters) {

        TirePosition tirePosition = (TirePosition) parameters.get("tirePosition");

        return MaintenanceTask.createTireService(vin, notes, tirePosition);
    }
}
