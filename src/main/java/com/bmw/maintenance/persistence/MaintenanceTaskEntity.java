package com.bmw.maintenance.persistence;

import com.bmw.maintenance.domain.ScannerType;
import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domain.TaskType;

import java.time.LocalDateTime;
import java.util.List;

import com.bmw.maintenance.domain.TirePosition;
import jakarta.persistence.*;
import lombok.*;

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
    private Long id;

    private String vin;

    @Enumerated(EnumType.STRING)
    private TaskType type;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private String notes;

    @Enumerated(EnumType.STRING)
    private TirePosition tirePosition;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> errorCodes;

    @Enumerated(EnumType.STRING)
    private ScannerType scannerType;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
