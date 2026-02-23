package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;

public interface MaintenanceTaskCreator {
    TaskType supports();
    MaintenanceTask create(CreateTaskCommand command);
}
