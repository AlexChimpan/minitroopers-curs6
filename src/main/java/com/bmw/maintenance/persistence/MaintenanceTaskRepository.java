package com.bmw.maintenance.persistence;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domaininteraction.MaintenanceTasks;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

/**
 * In-memory implementation of {@link MaintenanceTasks} for managing maintenance tasks.
 */
@ApplicationScoped
public class MaintenanceTaskRepository implements PanacheRepository<MaintenanceTask>, MaintenanceTasks {

    private final MaintenanceTaskMapper mapper;

    @Inject
    public MaintenanceTaskRepository(MaintenanceTaskMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public MaintenanceTask create(MaintenanceTask task) {
        MaintenanceTaskEntity entity = mapper.toEntity(task);
        entity.persist();
        return task;
    }

    @Override
    public MaintenanceTask findById(String taskId) {
        MaintenanceTaskEntity entity = MaintenanceTaskEntity.find("id",taskId).firstResult();
        if (entity == null) {
            throw new NotFoundException("Task not found: " + taskId);
        }
        return mapper.toDomain(entity);
    }

    @Override
    public void updateStatus(String taskId, TaskStatus newStatus) {
        if(findById(taskId)==null)
            throw new NotFoundException("Task not found: " + taskId);
        MaintenanceTaskEntity.update("status = ?1  where id = ?2 ", newStatus, Long.valueOf(taskId));
    }

    @Override
    public void upsertNotes(String taskId, String notes) {
        if(findById(taskId)==null)
            throw new NotFoundException("Task not found: " + taskId);
        MaintenanceTaskEntity.update("notes = ?1  where id =  ?2 ", notes, Long.valueOf(taskId));
    }

    @Override
    public List<MaintenanceTask> findAllTasks() {
        List<MaintenanceTaskEntity> entities = MaintenanceTaskEntity.listAll();
        List<MaintenanceTask> allTasks = entities.stream()
                .map(mapper::toDomain)
                .toList();
        return  allTasks;
    }

    @Override
    public List<MaintenanceTask> findByVin(String vin) {
        return findAllTasks().stream().filter(task -> task.getVin().equals(vin)).toList();
    }

}
