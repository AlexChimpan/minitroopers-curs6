package com.bmw.maintenance.persistence;

import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domain.details.ScannerType;
import com.bmw.maintenance.domain.details.TirePosition;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.tools.Diagnostic;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;


/**
 * Persistence entity for maintenance tasks.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "maintenance_task_entity")
public class MaintenanceTaskEntity {

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
    private ScannerType scannerType;
    private List<String> errorCodes;

}
