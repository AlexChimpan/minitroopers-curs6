package com.bmw.maintenance.persistence;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domaininteraction.MaintenanceTasks;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class MaintenanceTaskPanacheRepository implements MaintenanceTasks, PanacheRepository<MaintenanceTaskEntity> {
    private final MaintenanceTaskMapper mapper;

    @Inject
    public MaintenanceTaskPanacheRepository(MaintenanceTaskMapper mapper) {
        this.mapper = mapper;
    }
    @Override
    @Transactional
    public MaintenanceTask create(MaintenanceTask task) {
        MaintenanceTaskEntity entity = mapper.toEntity(task);
        this.persist(entity);// Panache method to persist the entity to the database
        return mapper.toDomain(entity);
    }

    @Override
    @Transactional
    public MaintenanceTask updateStatus(String taskId, TaskStatus newStatus) {
        MaintenanceTaskEntity foundEntity = this.findById(Long.parseLong(taskId));
        if (foundEntity == null) {
            throw new NotFoundException("Task not found: " + taskId);
        }
        foundEntity.setStatus(newStatus);
        this.persist(foundEntity);
        return mapper.toDomain(foundEntity);
    }

    @Override
    @Transactional
    public MaintenanceTask upsertNotes(String taskId, String notes) {
        MaintenanceTaskEntity foundEntity = this.findById(Long.parseLong(taskId));
        if (foundEntity == null) {
            throw new NotFoundException("Task not found: " + taskId);
        }
        foundEntity.setNotes(notes);
        this.persist(foundEntity);
        return mapper.toDomain(foundEntity);
    }

    @Override
    public MaintenanceTask findTaskById(String taskId) {
        MaintenanceTaskEntity foundEntity = this.findById(Long.parseLong(taskId));
        if (foundEntity == null) {
            throw new NotFoundException("Task not found: " + taskId);
        }
        return mapper.toDomain(foundEntity);
    }

    @Override
    public List<MaintenanceTask> findAllTasks() {
        return this.listAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<MaintenanceTask> findByVin(String vin) {
        return this.list("vin", vin).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
