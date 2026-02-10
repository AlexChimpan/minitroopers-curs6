package com.bmw.maintenance.persistence;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domaininteraction.MaintenanceTasks;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class MaintenanceTaskH2Repository implements MaintenanceTasks, PanacheRepository<MaintenanceTaskEntity> {

    private final MaintenanceTaskMapper mapper;

    @Inject
    public MaintenanceTaskH2Repository(MaintenanceTaskMapper mapper) { this.mapper = mapper; }

    @Override
    public MaintenanceTask create(MaintenanceTask task) {
        MaintenanceTaskEntity entity = mapper.toEntity(task);

        persist(entity);

        return mapper.toDomain(entity);
    }

    @Override
    public MaintenanceTask updateStatus(String taskId, TaskStatus newStatus) {
        Long id = Long.parseLong(taskId);
        MaintenanceTaskEntity entity = findById(id);

        if (entity == null) {
            throw new RuntimeException("Task not found: " + taskId);
        }

        entity.setStatus(newStatus);
        entity.setUpdatedAt(java.time.LocalDateTime.now());

        return mapper.toDomain(entity);
    }

    @Override
    public MaintenanceTask upsertNotes(String taskId, String notes) {
        Long id = Long.parseLong(taskId);
        MaintenanceTaskEntity entity = findById(id);

        if (entity == null) {
            throw new RuntimeException("Task not found: " + taskId);
        }

        entity.setNotes(notes);
        entity.setUpdatedAt(java.time.LocalDateTime.now());

        return mapper.toDomain(entity);
    }

    @Override
    public MaintenanceTask findById(String taskId) {
        Long id = Long.parseLong(taskId);
        MaintenanceTaskEntity entity = findById(id);

        if (entity == null) {
            throw new RuntimeException("Task not found: " + taskId);
        }

        return mapper.toDomain(entity);
    }

    @Override
    public List<MaintenanceTask> findAllTasks() {
        return listAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<MaintenanceTask> findByVin(String vin) {
        return list("vin", vin).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
