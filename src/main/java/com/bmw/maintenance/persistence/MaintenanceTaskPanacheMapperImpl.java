package com.bmw.maintenance.persistence;

import java.time.LocalDateTime;

import jakarta.enterprise.context.ApplicationScoped;

import com.bmw.maintenance.domain.MaintenanceTask;

/**
 * Maps between {@link MaintenanceTaskPanacheEntity} and {@link com.bmw.maintenance.domain.MaintenanceTask}.
 */
@ApplicationScoped
public class MaintenanceTaskPanacheMapperImpl implements MaintenanceTaskMapper<MaintenanceTaskPanacheEntity> {

    @Override
    public MaintenanceTask toDomain(MaintenanceTaskPanacheEntity entity) {
        return  MaintenanceTask.reconstitute(
                entity.getId(),
                entity.getVin(),
                entity.getType(),
                entity.getStatus(),
                entity.getNotes()
        );
    }

    @Override
    public MaintenanceTaskPanacheEntity toEntity(MaintenanceTask task) {
        return new MaintenanceTaskPanacheEntity(
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
