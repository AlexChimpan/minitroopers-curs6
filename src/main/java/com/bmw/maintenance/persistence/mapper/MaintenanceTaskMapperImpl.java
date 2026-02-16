package com.bmw.maintenance.persistence.mapper;

import jakarta.enterprise.context.ApplicationScoped;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.persistence.MaintenanceTaskEntity;

@ApplicationScoped
public class MaintenanceTaskMapperImpl implements MaintenanceTaskMapper {

    @Override
    public MaintenanceTask toDomain(MaintenanceTaskEntity entity) {
        return MaintenanceTask.reconstitute(
                entity.id,
                entity.vin,
                entity.type,
                entity.status,
                entity.notes
        );
    }

    @Override
    public MaintenanceTaskEntity toEntity(MaintenanceTask task) {
        MaintenanceTaskEntity entity = new MaintenanceTaskEntity();
        entity.vin = task.getVin();
        entity.type = task.getType();
        entity.status = task.getStatus();
        entity.notes = task.getNotes();
        return entity;
    }
}
