package com.bmw.maintenance.persistence;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domaininteraction.MaintenanceTasks;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

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
        return mapper.toDomain(entity);
    }

    @Override
    public MaintenanceTask updateStatus(String taskId, TaskStatus newStatus) {
        update("status = ?1  where id =  ?2 ", newStatus, taskId);
        return findById(taskId);
    }

    @Override
    public MaintenanceTask upsertNotes(String taskId, String notes) {

        if(mapper.toEntity(findById(taskId)).isPersistent()){
            update("notes = ?1  where id =  ?2 ", notes, taskId);
            return findById(taskId);
        }
        else {
            MaintenanceTask newTask = new MaintenanceTask();
            newTask.setNotes(notes);
            //create(new MaintenanceTask().builder().notes(notes).build());
            return create(newTask);

        }

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
