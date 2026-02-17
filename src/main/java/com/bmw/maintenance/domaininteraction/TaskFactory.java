package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;

import java.util.Map;

public interface TaskFactory {
    /**
     * Checks if this factory supports creating maintenance tasks of the specified type. It returns true if the provided TaskType is TIRE_SERVICE, indicating that this factory can create maintenance tasks of that type.
     * @param type The type of maintenance task to check for support. This method returns true if the provided TaskType is TIRE_SERVICE, indicating that this factory can create maintenance tasks of that type.
     * @return true if the provided TaskType is TIRE_SERVICE, false otherwise.
     * @see TaskType
     * @see MaintenanceTask
     */
    boolean supports(TaskType type);


    /**
     * A method to create a maintenance task
     * @param vin The Vehicle Identification Number (VIN) of the vehicle for which the maintenance task is being created. The VIN is a unique code used to identify individual motor vehicles, and it typically consists of 17 characters that include both letters and numbers. It provides information about the vehicle's manufacturer, model, year of production, and other relevant details.
     * @param notes Optional notes or comments related to the maintenance task. This parameter allows for additional information to be included when creating the maintenance task, such as specific instructions, observations, or any other relevant details that may assist in the execution of the task.
     * @param parameters A map of additional parameters that may be required for creating the maintenance task. The specific parameters included in this map will depend on the type of maintenance task being created. For example, if the task is a tire service, the parameters might include the tire position (e.g., front left, rear right) or other relevant details needed to properly create the task.
     * @return A MaintenanceTask object that represents the created maintenance task based on the provided VIN, notes, and additional parameters. The specific type of MaintenanceTask created will depend on the implementation of the factory and the type of task being created.
     */
    MaintenanceTask create(String vin, String notes, Map<String, Object> parameters);
}
