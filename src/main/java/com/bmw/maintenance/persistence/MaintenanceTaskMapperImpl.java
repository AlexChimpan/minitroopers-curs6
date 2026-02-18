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
        return  MaintenanceTask.reconstitute(
                entity.id,
                entity.getVin(),
                entity.getType(),
                entity.getStatus(),
                entity.getNotes(),
                entity.getTirePosition(),
                entity.getScannerType(),
                entity.getErrorCodes()
        );
    }

    @Override
    public MaintenanceTaskEntity toEntity(MaintenanceTask task) {
        return new MaintenanceTaskEntity(
                task.getTaskId(),
                task.getVin(),
                task.getType(),
                task.getStatus(),
                task.getNotes(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
