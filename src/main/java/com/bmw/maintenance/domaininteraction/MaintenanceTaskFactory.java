package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;

import java.util.Map;

/**
 * Factory for creating maintenance tasks.
 */
public interface MaintenanceTaskFactory {
    TaskType getSupportedType();
    MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData);
}
