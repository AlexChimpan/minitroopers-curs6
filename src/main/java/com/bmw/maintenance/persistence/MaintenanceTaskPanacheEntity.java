package com.bmw.maintenance.persistence;

import com.bmw.maintenance.domain.DiagnosticScanDetails;
import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domain.TaskType;

import java.time.LocalDateTime;

import com.bmw.maintenance.domain.TireServiceDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Persistence panache entity for maintenance tasks.
 */

@Entity
@Table(name = "maintenance_tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceTaskPanacheEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String vin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    private String notes;

    @Embedded
    private TireServiceDetailsEmbeddable tireServiceDetails;
    @Embedded
    private DiagnosticScanDetailsEmbeddable diagnosticScanDetails;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
