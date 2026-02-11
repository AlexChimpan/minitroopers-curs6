package com.bmw.maintenance.persistence;

import com.bmw.maintenance.domain.DiagnosticScanDetails;
import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domain.TaskType;

import java.time.LocalDateTime;

import com.bmw.maintenance.domain.TireServiceDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Persistence entity for maintenance tasks.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceTaskEntity {

    private Long id;
    private String vin;
    private TaskType type;
    private TaskStatus status;
    private String notes;
    private TireServiceDetails tireServiceDetails;
    private DiagnosticScanDetails diagnosticScanDetails;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
