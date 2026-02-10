package com.bmw.maintenance.persistence;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domaininteraction.MaintenanceTasks;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public class MaintenanceTaskRepository implements PanacheRepository<MaintenanceTaskEntity>,MaintenanceTasks {

    private final MaintenanceTaskMapper mapper;

    public MaintenanceTaskRepository(MaintenanceTaskMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public MaintenanceTask create(MaintenanceTask task) {
        MaintenanceTaskEntity entity = mapper.toEntity(task);
        entity.setCreatedAt(LocalDateTime.now());

        persist(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public MaintenanceTask updateStatus(String taskId, TaskStatus newStatus) {
        MaintenanceTaskEntity entity = findById(Long.valueOf(taskId));

        if(entity == null){
            throw new NotFoundException("Task not found: " + taskId);
        }

        entity.setStatus(newStatus);
        entity.setUpdatedAt(LocalDateTime.now());

        return mapper.toDomain(entity);
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
    public List<MaintenanceTask> findByVin(String vin) {
        return null;
    }


}
