package com.bmw.maintenance.persistence;

import com.bmw.maintenance.domain.ScannerType;
import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domain.TaskType;

import java.time.LocalDateTime;
import java.util.List;

import com.bmw.maintenance.domain.TirePosition;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA entity representing a maintenance task persisted in the database.
 *
 * <p>This class maps to the {@code MaintenanceTaskEntity} table and represents
 * a single maintenance task record for a vehicle. It uses Lombok annotations for
 * automatic generation of getters, setters, constructors, and other boilerplate code.
 * The entity is identified by an auto\-generated primary key and tracks creation
 * and update timestamps.</p>
 *
 * @see TaskStatus
 * @see TaskType
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
    private TirePosition tirePosition;
    private ScannerType scannerType;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> errorCodes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
