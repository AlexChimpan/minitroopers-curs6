package com.bmw.maintenance.domain;

import java.util.Map;

public interface MaintenanceTaskCreator {
    TaskType taskType();
    MaintenanceTask createTask(String vin, String notes, Map<String, Object> additionalData);
}
