package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.*;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.bmw.maintenance.domaininteraction.creation.MaintenanceTaskCreator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

/**
 * Service for creating and managing maintenance tasks.
 */

// Clasa care primeste cereri de la API (MaintenanceTaskResource) si construieste obiectele

@ApplicationScoped
public class MaintenanceTaskService {

    private final MaintenanceTasks maintenanceTasks;
    private  Map<TaskType, MaintenanceTaskCreator> creators = new EnumMap<TaskType, MaintenanceTaskCreator>(TaskType.class);

    /**
     * Creates a new service instance.
     *
     * @param maintenanceTasks backing repository
     */

    @Inject
    public MaintenanceTaskService(MaintenanceTasks maintenanceTasks, Instance<MaintenanceTaskCreator> discoveredCreators) {
        this.maintenanceTasks = maintenanceTasks;

        // Iterăm peste toate implementările descoperite de CDI
        for (MaintenanceTaskCreator creator : discoveredCreators) {

            // înregistrăm creatorul în map-ul registry-ului
            creators.put(creator.kindOfTask(), creator);
        }
    }


    /**
     * Creates a maintenance task for a vehicle.
     *
     * @param vin   vehicle identification number
     * @param type  task type
     * @param notes optional notes
     * @return created task id
     */
//    public Long createTask(String vin, TaskType type, String notes) {
//        MaintenanceTask task = switch (type) {
//            case OIL_CHANGE -> MaintenanceTask.createOilChange(vin, notes);
//            case BRAKE_INSPECTION -> MaintenanceTask.createBrakeInspection(vin, notes);
//            case TIRE_SERVICE -> null;        // astea nu trebuie sa fie null
//            case DIAGNOSTIC_SCAN -> null;
//        };
//
//        MaintenanceTask created = maintenanceTasks.create(task);
//        return created.getTaskId();
//    }

    public Long createTask(String vin, TaskType type, String notes, Map<String, Object> additionalData) {
        MaintenanceTaskCreator creator = creators.get(type);


        if (creator == null) {
            throw new IllegalArgumentException("In functia createTask nu e suportat task tipe: " + type);
        }

        MaintenanceTask task = creator.create(vin, notes, additionalData);
        return maintenanceTasks.create(task).getTaskId();
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
        return maintenanceTasks.findAll();
    }
}