package com.bmw.maintenance.persistence;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domaininteraction.MaintenanceTasks;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class MaintenanceTaskDbRepository implements MaintenanceTasks {

    private final MaintenanceTaskJpaRepository repository;

    @Inject
    public MaintenanceTaskDbRepository(MaintenanceTaskJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public MaintenanceTask create(MaintenanceTask task) {
        MaintenanceTaskJpaEntity entity = new MaintenanceTaskJpaEntity();

        entity.vin = task.getVin();
        entity.type = task.getType();
        entity.status = task.getStatus();
        entity.notes = task.getNotes();
        entity.tirePosition = task.getTirePosition();
        entity.errorCodes = task.getErrorCodes();
        entity.scannerType = task.getScannerType();

        repository.persist(entity);
        return MaintenanceTask.reconstitute(entity.id, entity.vin, entity.type, entity.status, entity.notes, entity.tirePosition, entity.errorCodes, entity.scannerType);
    }

    @Override
    public MaintenanceTask updateStatus(String taskId, TaskStatus newStatus) {
        Long id = Long.parseLong(taskId);
        MaintenanceTaskJpaEntity entity = repository.findById(id);

        if (entity == null) {
            throw new NotFoundException("Task not found: " + taskId);
        }

        entity.status = newStatus;
        return MaintenanceTask.reconstitute(entity.id, entity.vin, entity.type, entity.status, entity.notes, entity.tirePosition, entity.errorCodes, entity.scannerType);
    }

    @Override
    public MaintenanceTask upsertNotes(String taskId, String notes) {
        Long id = Long.parseLong(taskId);
        MaintenanceTaskJpaEntity entity = repository.findById(id);

        if (entity == null) {
            throw new NotFoundException("Task not found: " + taskId);
        }

        entity.notes = notes;

        return MaintenanceTask.reconstitute(entity.id, entity.vin, entity.type, entity.status, entity.notes, entity.tirePosition, entity.errorCodes, entity.scannerType);
    }

    @Override
    public MaintenanceTask findById(String taskId) {
        Long id = Long.parseLong(taskId);
        MaintenanceTaskJpaEntity entity = repository.findById(id);

        if (entity == null) {
            throw new NotFoundException("Task not found: " + taskId);
        }

        return MaintenanceTask.reconstitute(entity.id, entity.vin, entity.type, entity.status, entity.notes, entity.tirePosition, entity.errorCodes, entity.scannerType);
    }

    @Override
    public List<MaintenanceTask> findAllTasks() {
        return repository.listAll().stream()
                .map(entity -> MaintenanceTask.reconstitute(entity.id, entity.vin, entity.type, entity.status, entity.notes, entity.tirePosition, entity.errorCodes, entity.scannerType))
                .collect(Collectors.toList());
    }

    @Override
    public List<MaintenanceTask> findByVin(String vin) {
        return repository.findByVin(vin).stream()
                .map(entity -> MaintenanceTask.reconstitute(entity.id, entity.vin, entity.type, entity.status, entity.notes, entity.tirePosition, entity.errorCodes, entity.scannerType))
                .collect(Collectors.toList());
    }
}
