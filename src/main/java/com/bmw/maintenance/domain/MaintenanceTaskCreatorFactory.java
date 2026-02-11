package com.bmw.maintenance.domain;

import com.bmw.maintenance.domain.creators.MaintenanceTaskCreator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@ApplicationScoped
public class MaintenanceTaskCreatorFactory {

    private final Map<TaskType, MaintenanceTaskCreator> creators;

    @Inject
    public MaintenanceTaskCreatorFactory (Instance<MaintenanceTaskCreator> creatorsInstance)
    {
        creators=creatorsInstance.stream()
                .collect(Collectors.toMap(
                        MaintenanceTaskCreator::supports,
                        Function.identity()
                ));
    }

    public MaintenanceTaskCreator get(TaskType type)
    {
        MaintenanceTaskCreator maintenanceTaskCreator=creators.get(type);

        if(maintenanceTaskCreator == null)
            throw new IllegalArgumentException("No creator fount for type: "+type);

        return maintenanceTaskCreator;
    }
}
