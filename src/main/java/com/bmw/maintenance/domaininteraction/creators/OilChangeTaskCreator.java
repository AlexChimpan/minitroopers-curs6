package com.bmw.maintenance.domaininteraction.creators;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domaininteraction.MaintenanceTaskCreator;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;

@ApplicationScoped
public class OilChangeTaskCreator implements MaintenanceTaskCreator {
    @Override
    public TaskType supportedType(){
        return TaskType.OIL_CHANGE;
    }
    
    @Override
    public MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData){
        return MaintenanceTask.createOilChange(vin, notes);
    }
}
