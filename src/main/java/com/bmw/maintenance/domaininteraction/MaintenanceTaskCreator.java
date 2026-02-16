package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;

import java.util.Map;

public interface MaintenanceTaskCreator {

    String TIRE_POSITION = "tirePosition";
    String ERROR_CODES = "errorCodes";
    String SCANNER_TYPE = "scannerType";

    TaskType supports();

    MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData);
}
