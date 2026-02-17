package org.acme.service;

import java.util.List;

import org.acme.domain.MaintenanceTask;
import org.acme.domain.TaskStatus;

public interface MaintenanceTasksInterface {
    
    MaintenanceTask create(MaintenanceTask task);

    MaintenanceTask updateStatus(Long taskId, TaskStatus newStatus);

    MaintenanceTask upsertNotes(Long taskId, String notes);

    List<MaintenanceTask> findAll();

    List<MaintenanceTask> findByVin(String vin);
}
