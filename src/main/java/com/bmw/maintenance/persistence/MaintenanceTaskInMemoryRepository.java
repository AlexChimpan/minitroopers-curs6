package com.bmw.maintenance.persistence;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domaininteraction.MaintenanceTasks;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

/**
 * In-memory implementation of {@link MaintenanceTasks} for managing maintenance tasks.
 */
@ApplicationScoped
public class MaintenanceTaskInMemoryRepository implements MaintenanceTasks {

    private final MaintenanceTaskMapper mapper;
    private final MaintenanceTaskPanacheDao dao;

    @Inject
    public MaintenanceTaskInMemoryRepository(MaintenanceTaskMapper mapper, MaintenanceTaskPanacheDao dao) {
        this.mapper = mapper;
        this.dao = dao;
    }

    @Override
    public MaintenanceTask create(MaintenanceTask task) {
        MaintenanceTaskEntity entity = mapper.toEntity(task);

        dao.persist(entity);

        return mapper.toDomain(entity);
    }

    @Override
    public MaintenanceTask findById(String taskId) {
        Long id = Long.parseLong(taskId);
        Optional<MaintenanceTaskEntity> entityOpt = dao.findByIdOptional(id);

        if (entityOpt.isEmpty()) {
            throw new NotFoundException("Task not found: " + taskId);
        }

        return mapper.toDomain(entityOpt.get());
    }

    @Override
    public MaintenanceTask updateStatus(String taskId, TaskStatus newStatus) {
        Long id = Long.parseLong(taskId);
        MaintenanceTaskEntity entity = dao.findByIdOptional(id).orElseThrow(() -> new NotFoundException("Task not found: " + taskId));

        entity.setStatus(newStatus);
        entity.setUpdatedAt(LocalDateTime.now());

        dao.persist(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public MaintenanceTask upsertNotes(String taskId, String notes) {
        Long id = Long.parseLong(taskId);
        MaintenanceTaskEntity entity = dao.findByIdOptional(id).orElseThrow(() -> new NotFoundException("Task not found: " + taskId));

        entity.setNotes(notes);
        entity.setUpdatedAt(LocalDateTime.now());

        dao.persist(entity);

        return mapper.toDomain(entity);
    }

    @Override
    public List<MaintenanceTask> findAll() {
        return dao.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<MaintenanceTask> findByVin(String vin) {
        return dao.find("vin", vin).stream().map(mapper::toDomain).toList();
    }

}
