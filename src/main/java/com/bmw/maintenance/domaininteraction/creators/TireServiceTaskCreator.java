package com.bmw.maintenance.domaininteraction.creators;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domain.TirePosition;
import com.bmw.maintenance.domaininteraction.MaintenanceTaskCreator;
import com.bmw.maintenance.domaininteraction.TaskCreateKeys;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;

@ApplicationScoped
public class TireServiceTaskCreator implements MaintenanceTaskCreator {

    @Override
    public TaskType supportedType(){
        return TaskType.TIRE_SERVICE;
    }

    @Override
    public MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData){
        TirePosition tirePosition = (TirePosition) additionalData.get(TaskCreateKeys.TIRE_POSITION);
        return MaintenanceTask.createTireService(vin, tirePosition, notes);
    }

}
