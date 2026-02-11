package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;

import java.util.Map;

public interface MaintenanceType {
    MaintenanceTask create(String vin, TaskType type, String notes, Map<String, Object> additionalData);
}
