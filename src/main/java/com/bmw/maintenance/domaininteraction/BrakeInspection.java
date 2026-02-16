package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.ScannerType;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domain.TirePosition;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.Map;


@ApplicationScoped
@Named("BRAKE_INSPECTION")
public class BrakeInspection implements MaintenanceTaskTypes{

    /**
     * Creates a new brake inspection task in the \`IN\_PROGRESS\` status.
     *
     * @param vin   vehicle identification number
     * @param notes optional notes for the task
     * @return a new \`MaintenanceTask\` configured for brake inspection
     * @throws IllegalStateException if required business rules are not met
     */

    @Override
    public MaintenanceTask createTask(String vin, TaskType type, String notes, Map<String, Object> additionalData) {
        {
            return MaintenanceTask.createBrakeInspection(vin, notes);
        }
    }
}
