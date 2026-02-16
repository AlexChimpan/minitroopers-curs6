package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domain.TirePosition;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.BadRequestException;

import java.util.Map;

@ApplicationScoped
public class TireServiceTaskCreator implements MaintenanceTaskCreator {

    @Override
    public TaskType supports() {
        return TaskType.TIRE_SERVICE;
    }

    @Override
    public MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData) {
        var position = (TirePosition) additionalData.get(TIRE_POSITION);

        if (position == null) throw new BadRequestException("tirePosition is required for TIRE_SERVICE");

        return MaintenanceTask.createTireService(vin, notes, position);
    }
}
