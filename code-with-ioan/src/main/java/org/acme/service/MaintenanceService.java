package org.acme.service;

import java.util.List;

import org.acme.domain.MaintenanceTask;
import org.acme.domain.TaskStatus;
import org.acme.repository.MaintenanceRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class MaintenanceService implements MaintenanceTasksInterface{
    @Inject
    MaintenanceRepository maintenanceRepository;

    @Override
    public MaintenanceTask create(MaintenanceTask task) {
        try {
            if (task != null) {
                maintenanceRepository.persist(task);
                return task;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create maintenance task", e);
        }
    }

    @Override
    public MaintenanceTask updateStatus(Long taskId, TaskStatus newStatus) {
        try {
            MaintenanceTask task = maintenanceRepository.findById(taskId);
            if (task != null) {
                task.setStatus(newStatus);
                maintenanceRepository.persist(task); // Persist pentru updatw
                return task;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update maintenance task status", e);
        }
    }


    @Override
    public MaintenanceTask upsertNotes(Long taskId, String notes) {
        try {
            MaintenanceTask task = maintenanceRepository.findById(taskId);
            if (task != null) {
                task.setNotes(notes);
                maintenanceRepository.persist(task); // Persist pentru update
                return task;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Failed to upsert maintenance task notes", e);
        }
    }


    @Override
    public List<MaintenanceTask> findAll(){
        return maintenanceRepository.listAllTasks();
    }


    @Override
    public List<MaintenanceTask> findByVin(String vin){
        return maintenanceRepository.listAllTasks().stream().
            filter(task -> task.getVin().equals(vin)).
            toList();
    }

}
