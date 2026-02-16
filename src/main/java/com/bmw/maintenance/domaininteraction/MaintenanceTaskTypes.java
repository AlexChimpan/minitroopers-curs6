package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.ScannerType;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domain.TirePosition;

import java.util.Map;

public interface MaintenanceTaskTypes {

    public MaintenanceTask createTask(String vin, TaskType type, String notes, Map<String, Object> additionalNotes);
}


