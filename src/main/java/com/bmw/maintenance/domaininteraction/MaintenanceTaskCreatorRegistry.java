package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

import java.util.EnumMap;
import java.util.Map;

@ApplicationScoped
public class MaintenanceTaskCreatorRegistry {
    private final Map<TaskType, MaintenanceTaskCreator> byType;

    @Inject
    public MaintenanceTaskCreatorRegistry(Instance<MaintenanceTaskCreator> creator){
        EnumMap<TaskType, MaintenanceTaskCreator> map = new EnumMap<>(TaskType.class);
        for (MaintenanceTaskCreator c : creator){
            map.put(c.supportedType(), c);
        }
        this.byType = Map.copyOf(map);
    }
    public MaintenanceTaskCreator get(TaskType type){
        MaintenanceTaskCreator creator = byType.get(type);
        if(creator == null){
            throw new IllegalStateException("No creator registered for type: " + type);
        }
        return creator;
    }

}
