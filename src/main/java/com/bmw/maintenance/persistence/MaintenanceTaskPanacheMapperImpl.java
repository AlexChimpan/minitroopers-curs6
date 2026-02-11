package com.bmw.maintenance.persistence;

import java.time.LocalDateTime;

import com.bmw.maintenance.domain.DiagnosticScanDetails;
import com.bmw.maintenance.domain.TireServiceDetails;
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
                entity.getNotes(),
                entity.getTireServiceDetails() != null ? new TireServiceDetails(entity.getTireServiceDetails().getTirePosition()):null,
                entity.getDiagnosticScanDetails() != null ? new DiagnosticScanDetails(entity.getDiagnosticScanDetails().getScannerType(), entity.getDiagnosticScanDetails().getErrorCodes()):null
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
                task.getTireServiceDetails() != null ? new TireServiceDetailsEmbeddable(task.getTireServiceDetails().getTirePosition()):null,
                task.getDiagnosticScanDetails() != null ? new DiagnosticScanDetailsEmbeddable(task.getDiagnosticScanDetails().getScannerType(), task.getDiagnosticScanDetails().getErrorCodes()):null,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
