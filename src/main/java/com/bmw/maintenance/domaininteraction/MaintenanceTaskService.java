package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domain.TaskType;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Service for creating and managing maintenance tasks.
 */
@ApplicationScoped
public class MaintenanceTaskService {

    private final MaintenanceTasks maintenanceTasks;
    private final MaintenanceTaskCreatorFactory creatorFactory;

    /**
     * Creates a new service instance.
     *
     * @param maintenanceTasks backing repository
     */
    @Inject
    public MaintenanceTaskService(MaintenanceTasks maintenanceTasks, MaintenanceTaskCreatorFactory creatorFactory) {
        this.maintenanceTasks = maintenanceTasks;
        this.creatorFactory = creatorFactory;
    }

    /**
     * Creates a maintenance task for a vehicle.
     *
     * @param command encapsulated creation data
     * @return result wrapping the created task id or validation errors
     */
    public Result<Long> createTask(CreateTaskCommand command) {
        try {
            MaintenanceTask task = creatorFactory.get(command.type()).create(command);
            MaintenanceTask created = maintenanceTasks.create(task);
            return Result.ok(created.getTaskId());
        } catch (IllegalArgumentException | IllegalStateException ex) {
            return Result.failure(new DomainError("VALIDATION_ERROR", ex.getMessage()));
        }
    }

    /**
     * Updates the status of a task.
     *
     * @param taskId    task id
     * @param newStatus new status
     */
    public void updateTaskStatus(String taskId, TaskStatus newStatus) {
        MaintenanceTask task = maintenanceTasks.findById(taskId);

        switch (newStatus) {
            case IN_PROGRESS -> task.start();
            case DONE -> task.complete();
            case NEW -> {
                throw new IllegalArgumentException("Transition back to NEW is not supported");
            }
        }

        maintenanceTasks.create(task);
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