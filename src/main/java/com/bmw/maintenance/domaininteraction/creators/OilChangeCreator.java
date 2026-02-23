package com.bmw.maintenance.domaininteraction.creators;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domaininteraction.CreateTaskCommand;
import com.bmw.maintenance.domaininteraction.MaintenanceTaskCreator;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OilChangeCreator implements MaintenanceTaskCreator {

    @Override
    public TaskType supports() {
        return TaskType.OIL_CHANGE;
    }

    @Override
    public MaintenanceTask create(CreateTaskCommand command) {
        return MaintenanceTask.createOilChange(command.vin(), command.notes());
    }
}
