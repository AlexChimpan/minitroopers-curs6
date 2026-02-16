package com.bmw.maintenance.persistence;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domaininteraction.MaintenanceTasks;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class MaintenanceTaskDBRepository implements MaintenanceTasks,PanacheRepository<MaintenanceTaskPanacheEntity>{

    private final MaintenanceTaskMapper<MaintenanceTaskPanacheEntity> mapper;

    @Inject
    public MaintenanceTaskDBRepository( MaintenanceTaskMapper<MaintenanceTaskPanacheEntity> mapper) {
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public MaintenanceTask create(MaintenanceTask task) {
        MaintenanceTaskPanacheEntity maintenanceTaskEntity=mapper.toEntity(task);
        persist(maintenanceTaskEntity);
        return mapper.toDomain(maintenanceTaskEntity);
    }

    @Override
    @Transactional
    public MaintenanceTask updateStatus(String taskId, TaskStatus newStatus) {
        MaintenanceTaskPanacheEntity maintenanceTaskEntity=findById(Long.valueOf(taskId));

        if (maintenanceTaskEntity == null) {
            throw new NotFoundException("Task not found: " + taskId);
        }

        maintenanceTaskEntity.setStatus(newStatus);
        maintenanceTaskEntity.setUpdatedAt(LocalDateTime.now());

        return mapper.toDomain(maintenanceTaskEntity);
    }

    @Override
    @Transactional
    public MaintenanceTask upsertNotes(String taskId, String notes) {
        MaintenanceTaskPanacheEntity maintenanceTaskEntity=findById(Long.parseLong(taskId));

        if (maintenanceTaskEntity == null) {
            throw new NotFoundException("Task not found: " + taskId);
        }

        maintenanceTaskEntity.setNotes(notes);
        maintenanceTaskEntity.setUpdatedAt(LocalDateTime.now());

        return mapper.toDomain(maintenanceTaskEntity);
    }

    @Override
    public MaintenanceTask findById(String taskId) {

        MaintenanceTaskPanacheEntity maintenanceTaskEntity=findById(Long.parseLong(taskId));

        if(maintenanceTaskEntity == null){
            throw new NotFoundException("Task not found: "+taskId);
        }

        return mapper.toDomain(maintenanceTaskEntity);
    }

    @Override
    public List<MaintenanceTask> findAllTasks() {
        return findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<MaintenanceTask> findByVin(String vin) {
        return find("vin",vin)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
