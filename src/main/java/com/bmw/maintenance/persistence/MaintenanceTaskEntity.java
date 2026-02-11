package com.bmw.maintenance.persistence;

import com.bmw.maintenance.domain.ScannerType;
import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domain.TaskType;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.bmw.maintenance.domain.TirePosition;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
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

// Panache ofera direct campul id
@Entity
public class MaintenanceTaskEntity extends PanacheEntity {

    private Long id;
    private String vin;
    private TaskType type;
    private TaskStatus status;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Tire service
    public TirePosition tirePosition;

    // Diagnostic scan
    private ArrayList<String> errorCodes;
    private ScannerType scannerType;


}
