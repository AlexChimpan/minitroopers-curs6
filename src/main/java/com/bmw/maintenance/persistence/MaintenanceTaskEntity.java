package com.bmw.maintenance.persistence;

import com.bmw.maintenance.domain.ScannerType;
import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domain.TaskType;

import java.time.LocalDateTime;

import com.bmw.maintenance.domain.TirePosition;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
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
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceTaskEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue
    private Long id;
    private String vin;
    private TaskType type;
    private TaskStatus status;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private TirePosition tirePosition;
    private String[] errorCodes;
    private ScannerType scannerType;

}
