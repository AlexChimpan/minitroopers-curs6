package com.bmw.maintenance.persistence;

import java.time.LocalDateTime;

import jakarta.enterprise.context.ApplicationScoped;

import com.bmw.maintenance.domain.MaintenanceTask;

/**
 * Maps between {@link MaintenanceTaskEntity} and {@link com.bmw.maintenance.domain.MaintenanceTask}.
 */
@ApplicationScoped
public class MaintenanceTaskMapperImpl implements MaintenanceTaskMapper {

    @Override
    public MaintenanceTask toDomain(MaintenanceTaskEntity entity) {
        if (entity == null) return null;

        return  MaintenanceTask.reconstitute(
                entity.getId(),
                entity.getVin(),
                entity.getType(),
                entity.getStatus(),
                entity.getNotes(),
                entity.getTirePosition(),
                entity.getErrorCodes(),
                entity.getScannerType()
        );
    }

    @Override
    public MaintenanceTaskEntity toEntity(MaintenanceTask task) {
        if (task == null) return null;

        MaintenanceTaskEntity entity = new MaintenanceTaskEntity();

        entity.setId(task.getTaskId());
        entity.setVin(task.getVin());
        entity.setType(task.getType());
        entity.setStatus(task.getStatus());
        entity.setNotes(task.getNotes());
        entity.setTirePosition(task.getTirePosition());
        entity.setErrorCodes(task.getErrorCodes());
        entity.setScannerType(task.getScannerType());

        return entity;
    }
}
