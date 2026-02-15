package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domain.TaskType;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import com.bmw.maintenance.domaininteraction.Tasks.TaskCreator;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

/**
 * Service for creating and managing maintenance tasks.
 */
@ApplicationScoped
public class MaintenanceTaskService {

    private final MaintenanceTasks maintenanceTasks;
    private Map<TaskType, TaskCreator> byType;

    @Inject
    private Instance<TaskCreator> creators;


    @PostConstruct
    void init() {
        byType = new EnumMap<>(TaskType.class);
        for (TaskCreator c : creators) {
            TaskType type = c.supports();
            TaskCreator prev = byType.putIfAbsent(type, c);
            if (prev != null) {
                throw new IllegalStateException("Duplicat TaskCreator pentru " + type);
            }
        }
    }


    /**
     * Creates a new service instance.
     *
     * @param maintenanceTasks backing repository
     */
    public MaintenanceTaskService(MaintenanceTasks maintenanceTasks) {
        this.maintenanceTasks = maintenanceTasks;
    }

    /**
     * Creates a maintenance task for a vehicle.
     *
     * @param vin   vehicle identification number
     * @param type  task type
     * @param notes optional notes
     * @return created task id
     */
    public Long createTask(String vin, TaskType type, String notes, Map<String, Object> additionalData) {
        TaskCreator taskCreator = byType.get(type);
        if (taskCreator == null) {
            throw new IllegalArgumentException("Doesn't exist task creator for type " + type);
        }

        MaintenanceTask task = taskCreator.create(vin, notes, additionalData);
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