package com.bmw.maintenance.persistence;

import com.bmw.maintenance.domain.ScannerType;
import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domain.TaskType;

import java.time.LocalDateTime;
import java.util.List;

import com.bmw.maintenance.domain.TirePosition;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
@Entity
public class MaintenanceTaskEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String vin;
    private TaskType type;
    private TaskStatus status;
    private TirePosition tirePosition;
    private ScannerType scannerType;
    private List<String> errorCodes;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
