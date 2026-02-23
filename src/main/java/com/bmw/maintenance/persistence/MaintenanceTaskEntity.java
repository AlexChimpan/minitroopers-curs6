package com.bmw.maintenance.persistence;

import com.bmw.maintenance.domain.*;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "maintenance_task")
@Getter
@Setter
public class MaintenanceTaskEntity extends PanacheEntity {

    @Column(nullable = false)
    private String vin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @Column
    private String notes;

    @Enumerated(EnumType.STRING)
    private TirePosition tirePosition;

    @Enumerated(EnumType.STRING)
    private ScannerType scannerType;

    @ElementCollection
    @CollectionTable(name = "maintenance_task_error_codes", joinColumns = @JoinColumn(name = "task_id"))
    @Column(name = "error_code")
    private List<String> errorCodes;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public MaintenanceTaskEntity() { }

    public MaintenanceTaskEntity(Long id, String vin, TaskType type, TaskStatus status, String notes,
                                 LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.vin = vin;
        this.type = type;
        this.status = status;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
