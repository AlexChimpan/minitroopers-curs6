package com.bmw.maintenance.domaininteraction.Tasks;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;

import java.util.Map;

public interface TaskCreator {

    TaskType supports();
    MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData);

}
