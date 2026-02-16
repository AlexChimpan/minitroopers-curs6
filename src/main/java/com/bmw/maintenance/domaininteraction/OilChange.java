package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.Map;


@ApplicationScoped
@Named("OIL_CHANGE")
public class OilChange implements MaintenanceTaskTypes{

    /**
     * Creates a new oil change task in the \`IN\_PROGRESS\` status.
     *
     * @param vin   vehicle identification number
     * @param notes optional notes for the task
     * @return a new \`MaintenanceTask\` configured for oil change
     * @throws IllegalStateException if required business rules are not met
     */

    @Override
    public MaintenanceTask createTask(String vin, TaskType type, String notes, Map<String, Object> additionalData) {
        {
            return MaintenanceTask.createOilChange(vin, notes);
        }
    }
}
