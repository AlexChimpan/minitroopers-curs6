package com.bmw.maintenance.domaininteraction.creators;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domaininteraction.CreateTaskCommand;
import com.bmw.maintenance.domaininteraction.MaintenanceTaskCreator;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BrakeInspectionCreator implements MaintenanceTaskCreator {

    @Override
    public TaskType supports() {
        return TaskType.BRAKE_INSPECTION;
    }

    @Override
    public MaintenanceTask create(CreateTaskCommand command) {
        return MaintenanceTask.createBrakeInspection(command.vin(), command.notes());
    }
}
