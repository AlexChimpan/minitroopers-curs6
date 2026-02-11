package com.bmw.maintenance.domaininteraction.creation;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.persistence.MaintenanceTaskEntity;

import java.util.Map;

public interface MaintenanceTaskCreator {
    TaskType kindOfTask();      // pentru ce tip se aplica
    MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData);
}
