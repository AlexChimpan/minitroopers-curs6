package com.bmw.maintenance.persistence;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domaininteraction.MaintenanceTasks;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class MaintenanceTaskPanacheRepository implements PanacheRepository<MaintenanceTaskEntity>, MaintenanceTasks {

    private final MaintenanceTaskMapper mapper;

    @Inject
    public MaintenanceTaskPanacheRepository(MaintenanceTaskMapper mapper) { this.mapper = mapper; }

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
            throw new NotFoundException("Task not found: " + taskId);
        }

        entity.setStatus(newStatus);

        return mapper.toDomain(entity);
    }

    @Override
    public MaintenanceTask upsertNotes(String taskId, String notes) {
        Long id = Long.parseLong(taskId);
        MaintenanceTaskEntity entity = findById(id);

        if (entity == null) {
            throw new NotFoundException("Task not found: " + taskId);
        }

        entity.setNotes(notes);

        return mapper.toDomain(entity);
    }

    @Override
    public MaintenanceTask findById(String taskId) {
        Long id = Long.parseLong(taskId);
        MaintenanceTaskEntity entity = findById(id);

        if (entity == null) {
            throw new NotFoundException("Task not found: " + taskId);
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
