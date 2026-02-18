package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.TaskType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

import java.util.EnumMap;
import java.util.Map;

@ApplicationScoped
public class MaintenanceTaskCreatorFactory {

    private final Map<TaskType, MaintenanceTaskCreator> creators = new EnumMap<>(TaskType.class);

    @Inject
    public MaintenanceTaskCreatorFactory(Instance<MaintenanceTaskCreator> creatorBeans) {
        for (MaintenanceTaskCreator c : creatorBeans) {
            creators.put(c.supports(), c);
        }
    }

    public MaintenanceTaskCreator get(TaskType type) {
        MaintenanceTaskCreator c = creators.get(type);
        if (c == null) {
            throw new IllegalArgumentException("No creator found for type: " + type);
        }
        return c;
    }
}
