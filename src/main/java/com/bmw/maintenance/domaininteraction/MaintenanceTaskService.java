package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.*;

import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;

/**
 * Service for creating and managing maintenance tasks.
 */
@ApplicationScoped
public class MaintenanceTaskService {

    private final Instance<MaintenanceTaskCreator> creators;
    private final MaintenanceTasks maintenanceTasks;

    /**
     * Creates a new service instance.
     *
     * @param maintenanceTasks backing repository
     * @param creators         injected task creators
     */
    public MaintenanceTaskService(MaintenanceTasks maintenanceTasks, Instance<MaintenanceTaskCreator> creators) {
        this.maintenanceTasks = maintenanceTasks;
        this.creators = creators;
    }

    /**
     * Creates a maintenance task for a vehicle.
     *
     * @param vin            vehicle identification number
     * @param type           task type
     * @param notes          optional notes
     * @param additionalData additional data required by specific task types
     * @return created task id
     */
    public Long createTask(String vin, TaskType type, String notes, Map<String, Object> additionalData) {

        if (type == null) throw new IllegalArgumentException("Type must not be null");

        if (additionalData == null) additionalData = Map.of();

        MaintenanceTaskCreator creator = creators.stream()
                .filter(c -> c.supports() == type)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported task type: " + type));

        MaintenanceTask task = creator.create(vin, notes, additionalData);

        MaintenanceTask created = maintenanceTasks.create(task);
        return created.getTaskId();
    }


    /**
     * Updates the status of a task.
     *
     * @param taskId    task id
     * @param newStatus new status
     */
    public void updateTaskStatus(String taskId, TaskStatus newStatus) {
        maintenanceTasks.updateStatus(taskId, newStatus);
    }

    /**
     * Adds or updates notes for a task.
     *
     * @param taskId task id
     * @param notes  notes to store
     */
    public void addOrUpdateNotes(String taskId, String notes) {
        maintenanceTasks.upsertNotes(taskId, notes);
    }

    /**
     * Gets a task by id.
     *
     * @param taskId task id
     * @return task or null if not found
     */
    public MaintenanceTask getTaskById(String taskId) {
        return maintenanceTasks.findById(taskId);
    }

    /**
     * Lists tasks for a VIN or all tasks when VIN is blank.
     *
     * @param vin vehicle identification number
     * @return matching tasks
     */
    public List<MaintenanceTask> listTasks(String vin) {
        if (vin != null && !vin.isBlank()) {
            return maintenanceTasks.findByVin(vin);
        }
        return maintenanceTasks.findAllTasks();
    }
}