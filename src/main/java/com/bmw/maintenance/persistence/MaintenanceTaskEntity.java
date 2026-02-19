package com.bmw.maintenance.persistence;

import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domain.TaskType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

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

    @Id @GeneratedValue private Long id;
    private String vin;
    @Enumerated(EnumType.STRING) private TaskType type;
    @Enumerated(EnumType.STRING) private TaskStatus status;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
