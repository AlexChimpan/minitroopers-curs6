package com.bmw.maintenance.persistence;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domaininteraction.MaintenanceTasks;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.bmw.maintenance.persistence.mapper.MaintenanceTaskMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class MaintenanceTaskInMemoryRepository implements MaintenanceTasks {

    private final MaintenanceTaskMapper mapper;
    private final MaintenanceTaskRepository repository;


    @Inject
    public MaintenanceTaskInMemoryRepository(MaintenanceTaskMapper mapper, MaintenanceTaskRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public MaintenanceTask create(MaintenanceTask task) {
        MaintenanceTaskEntity entity = mapper.toEntity(task);
        repository.persist(entity);

        return mapper.toDomain(entity);
    }

    @Override
    public MaintenanceTask findById(String taskId) {
        Long id = Long.parseLong(taskId);
        MaintenanceTaskEntity entity = repository.findById(id);

        if (entity == null) {
            throw new NotFoundException("Task not found: " + taskId);
        }

        return mapper.toDomain(entity);
    }

    @Override
    public MaintenanceTask updateStatus(String taskId, TaskStatus newStatus) {
        Long id = Long.parseLong(taskId);
        MaintenanceTaskEntity entity = repository.findById(id);

        if (entity == null) {
            throw new NotFoundException("Task not found: " + taskId);
        }

        entity.setStatus(newStatus);
        entity.setUpdatedAt(LocalDateTime.now());

        repository.persist(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public MaintenanceTask upsertNotes(String taskId, String notes) {
        Long id = Long.parseLong(taskId);
        MaintenanceTaskEntity entity = repository.findById(id);

        if (entity == null) {
            throw new NotFoundException("Task not found: " + taskId);
        }

        entity.setNotes(notes);
        entity.setUpdatedAt(LocalDateTime.now());

        repository.persist(entity);

        return mapper.toDomain(entity);
    }

    @Override
    public List<MaintenanceTask> findAllTasks() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<MaintenanceTask> findByVin(String vin) {
        return repository.find("vin", vin).stream()
                .filter(entity -> vin.equals(entity.getVin()))
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

}
