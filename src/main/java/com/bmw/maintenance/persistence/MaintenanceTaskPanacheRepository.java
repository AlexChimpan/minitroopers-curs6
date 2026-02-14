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

@ApplicationScoped
@Transactional
public class MaintenanceTaskPanacheRepository implements MaintenanceTasks, PanacheRepository<MaintenanceTaskEntity> {

    private static final MaintenanceTaskMapper mapper = new MaintenanceTaskMapperImpl();

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
        entity.setUpdatedAt(LocalDateTime.now());

        return mapper.toDomain(entity);
    }

    @Override
    public MaintenanceTask upsertNotes(String taskId, String notes){
        Long id = Long.parseLong(taskId);
        MaintenanceTaskEntity entity = findById(id);

        if (entity == null) {
            throw new NotFoundException("Task not found: " + taskId);
        }

        entity.setNotes(notes);
        entity.setUpdatedAt(LocalDateTime.now());

        return mapper.toDomain(entity);
    }

    @Override
    public MaintenanceTask findById(String taskId){
        Long id = Long.parseLong(taskId);
        MaintenanceTaskEntity entity = findById(id);

        if (entity == null) {
            throw new NotFoundException("Task not found: " + taskId);
        }

        return mapper.toDomain(entity);
    }

    @Override
    public List<MaintenanceTask> findAllTasks(){
        return listAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<MaintenanceTask> findByVin(String vin){
        return list("vin", vin).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
