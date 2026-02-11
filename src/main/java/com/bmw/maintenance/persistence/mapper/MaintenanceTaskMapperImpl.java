package com.bmw.maintenance.persistence.mapper;

import java.time.LocalDateTime;

import com.bmw.maintenance.persistence.MaintenanceTaskEntity;
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
                entity.getId(),
                entity.getVin(),
                entity.getType(),
                entity.getStatus(),
                entity.getNotes()
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
