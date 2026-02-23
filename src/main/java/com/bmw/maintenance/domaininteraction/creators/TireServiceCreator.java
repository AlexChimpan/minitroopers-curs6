package com.bmw.maintenance.domaininteraction.creators;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domain.TirePosition;
import com.bmw.maintenance.domaininteraction.CreateTaskCommand;
import com.bmw.maintenance.domaininteraction.MaintenanceTaskCreator;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TireServiceCreator implements MaintenanceTaskCreator {

    @Override
    public TaskType supports() {
        return TaskType.TIRE_SERVICE;
    }

    @Override
    public MaintenanceTask create(CreateTaskCommand command) {
        TirePosition pos = command.tirePosition();
        if (pos == null) {
            throw new IllegalArgumentException("tirePosition is required for TIRE_SERVICE");
        }

        return MaintenanceTask.createTireService(command.vin(), command.notes(), pos);
    }
}
