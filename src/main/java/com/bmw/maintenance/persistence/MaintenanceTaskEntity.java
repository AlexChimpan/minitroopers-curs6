package com.bmw.maintenance.persistence;

import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domain.TaskType;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "maintenance_task")
public class MaintenanceTaskEntity {
    @Id
    private Long id;
    private String vin;
    private TaskType type;
    private TaskStatus status;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
