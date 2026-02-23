package com.bmw.maintenance.persistence;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domaininteraction.MaintenanceTasks;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class MaintenanceTaskRepository implements MaintenanceTasks {

    @Inject MaintenanceTaskPanacheRepository panache;
    @Inject MaintenanceTaskMapper mapper;

    @Override
    @Transactional
    public MaintenanceTask create(MaintenanceTask task) {
        MaintenanceTaskEntity entity = mapper.toEntity(task);

        if (entity.id == null) {
            entity.setCreatedAt(LocalDateTime.now());
            entity.setUpdatedAt(LocalDateTime.now());
            panache.persist(entity);
            return mapper.toDomain(entity);
        }

        MaintenanceTaskEntity existing = panache.findById(entity.id);
        if (existing == null) {
            throw new IllegalArgumentException("Task not found: " + entity.id);
        }

        existing.setVin(entity.getVin());
        existing.setType(entity.getType());
        existing.setStatus(entity.getStatus());
        existing.setNotes(entity.getNotes());
        existing.setTirePosition(entity.getTirePosition());
        existing.setScannerType(entity.getScannerType());
        existing.setErrorCodes(entity.getErrorCodes());
        existing.setUpdatedAt(LocalDateTime.now());

        return mapper.toDomain(existing);
    }

    @Override
    @Transactional
    public MaintenanceTask updateStatus(String taskId, TaskStatus newStatus) {
        Long id = Long.parseLong(taskId);
        MaintenanceTaskEntity existing = panache.findById(id);
        if (existing == null) throw new IllegalArgumentException("Task not found: " + taskId);

        existing.setStatus(newStatus);
        existing.setUpdatedAt(LocalDateTime.now());
        return mapper.toDomain(existing);
    }

    @Override
    @Transactional
    public MaintenanceTask upsertNotes(String taskId, String notes) {
        Long id = Long.parseLong(taskId);
        MaintenanceTaskEntity existing = panache.findById(id);
        if (existing == null) throw new IllegalArgumentException("Task not found: " + taskId);

        existing.setNotes(notes);
        existing.setUpdatedAt(LocalDateTime.now());
        return mapper.toDomain(existing);
    }

    @Override
    public MaintenanceTask findById(String taskId) {
        Long id = Long.parseLong(taskId);
        MaintenanceTaskEntity entity = panache.findById(id);
        if (entity == null) throw new IllegalArgumentException("Task not found: " + taskId);
        return mapper.toDomain(entity);
    }

    @Override
    public List<MaintenanceTask> findAllTasks() {
        return panache.streamAll().map(mapper::toDomain).toList();
    }

    @Override
    public List<MaintenanceTask> findByVin(String vin) {
        return panache.find("vin", vin).stream().map(mapper::toDomain).toList();
    }
}
