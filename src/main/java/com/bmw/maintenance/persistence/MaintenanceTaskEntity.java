package com.bmw.maintenance.persistence;

import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domain.TaskType;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Persistence entity for maintenance tasks.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceTaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String vin;
    private TaskType type;
    private TaskStatus status;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
