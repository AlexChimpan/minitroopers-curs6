package com.bmw.maintenance.persistence;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domaininteraction.MaintenanceTasks;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.util.List;

public class MaintenanceTaskPanacheRepository implements MaintenanceTasks, PanacheRepository<MaintenanceTaskPanacheEntity> {
    @Override
    public MaintenanceTask create(MaintenanceTask task) {

    }

    @Override
    public MaintenanceTask updateStatus(String taskId, TaskStatus newStatus) {
        return null;
    }

    @Override
    public MaintenanceTask upsertNotes(String taskId, String notes) {
        return null;
    }

    @Override
    public MaintenanceTask findById(String taskId) {
        return null;
    }

    @Override
    public List<MaintenanceTask> findAllTasks() {
        return List.of();
    }

    @Override
    public List<MaintenanceTask> findByVin(String vin) {
        return List.of();
    }
}
