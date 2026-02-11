package com.bmw.maintenance.persistence;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domaininteraction.MaintenanceTasks;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class MaintanenceTasksDatabaseImplementation implements MaintenanceTasks {

    @Inject
    MaintenanceTaskRepositoryInH2Database repository;

    @Inject
    MaintenanceTaskMapper mapper;


    @Override
    public MaintenanceTask create(MaintenanceTask task) {
        MaintenanceTaskEntity taskEntity = mapper.toEntity(task);
        taskEntity.persist();           // face insert in baza de date
        return mapper.toDomain(taskEntity);
    }

    @Override
    public MaintenanceTask updateStatus(String taskId, TaskStatus newStatus) {
        MaintenanceTaskEntity entity = repository.findById(Long.valueOf(taskId));
        if (entity == null) {
            throw new NotFoundException("Task not found for update status");
        }
        entity.setStatus(newStatus);
        entity.setUpdatedAt(LocalDateTime.now());
        return mapper.toDomain(entity);
    }

    @Override
    public MaintenanceTask upsertNotes(String taskId, String notes) {
        MaintenanceTaskEntity entity = repository.findById(Long.valueOf(taskId));
        if (entity == null) {
            throw new NotFoundException("Task not found for update notes");
        }
        entity.setNotes(notes);
        entity.setUpdatedAt(LocalDateTime.now());
        return mapper.toDomain(entity);
    }

    @Override
    public MaintenanceTask findById(String taskId) {
        MaintenanceTaskEntity entity = repository.findById(Long.valueOf(taskId));
        if (entity == null) {
            throw new NotFoundException("Task not found for find by id");
        }
        return mapper.toDomain(entity);
    }

    @Override
    public List<MaintenanceTask> findAll() {
        return repository.listAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<MaintenanceTask> findByVin(String vin) {
        return repository.find("vin", vin)
                .list().stream().map(mapper::toDomain) .toList();
    }
}
