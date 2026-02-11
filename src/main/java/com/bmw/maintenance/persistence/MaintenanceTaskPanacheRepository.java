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
@Transactional
public class MaintenanceTaskPanacheRepository implements MaintenanceTasks, PanacheRepository<MaintenanceTask> {

    @Override
    public MaintenanceTask create(MaintenanceTask task) {
        persist(task);
        return task;
    }

    @Override
    public MaintenanceTask updateStatus(String taskId, TaskStatus newStatus) {
        Long id = Long.parseLong(taskId);
        MaintenanceTask task = findById(id);

        if (task == null) {
            throw new NotFoundException("Task not found: " + taskId);
        }

        task.setStatus(newStatus);

        return getEntityManager().merge(task);
    }

    @Override
    public MaintenanceTask upsertNotes(String taskId, String notes){
        Long id = Long.parseLong(taskId);
        MaintenanceTask task = findById(id);

        if (task == null) {
            throw new NotFoundException("Task not found: " + taskId);
        }

        task.setNotes(notes);

        return getEntityManager().merge(task);
    }

    @Override
    public MaintenanceTask findById(String taskId){
        Long id = Long.parseLong(taskId);
        MaintenanceTask task = findById(id);

        if (task == null) {
            throw new NotFoundException("Task not found: " + taskId);
        }

        return task;
    }

    @Override
    public List<MaintenanceTask> findAllTasks(){
        return listAll();
    }

    @Override
    public List<MaintenanceTask> findByVin(String vin){
        return list("vin", vin);
    }
}
