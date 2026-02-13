package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.literal.NamedLiteral;

import java.util.List;
import java.util.Map;

/**
 * Service for creating and managing maintenance tasks.
 */
@ApplicationScoped
public class MaintenanceTaskService {

    private final MaintenanceTasks maintenanceTasks;
    private final Instance<MaintenanceTaskFactory> factory;
    /**
     * Creates a new service instance.
     *
     * @param maintenanceTasks backing repository
     */
    public MaintenanceTaskService(MaintenanceTasks maintenanceTasks, Instance<MaintenanceTaskFactory> factory) {
        this.maintenanceTasks = maintenanceTasks;
        this.factory = factory;
    }

    /**
     * Creates a maintenance task for a vehicle.
     *
     * @param vin           vehicle identification number
     * @param type          task type
     * @param notes         optional notes
     * @return created task id
     */
    public Long createTask(String vin, TaskType type, String notes, Map<String, Object> additionalData) {
        MaintenanceTask task = factory.select(NamedLiteral.of(type.toString())).get().create(vin, type, notes, additionalData);

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