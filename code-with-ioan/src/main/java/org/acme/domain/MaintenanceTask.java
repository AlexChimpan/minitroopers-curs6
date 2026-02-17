package org.acme.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity
@Table(name = "tasks")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceTask extends PanacheEntity {
    @Column(name = "vin",nullable = false)
    private String vin;

    @Column(name = "task_type", nullable = false)
    private TaskType type;

    @Column(name = "task_status", nullable = false)
    private TaskStatus status;

    @Column(name = "notes")
    private String notes;

    public static MaintenanceTask createOilChange(String vin, String notes) {
        return MaintenanceTask.builder()
                .vin(vin)
                .type(TaskType.OIL_CHANGE)
                .status(TaskStatus.PENDING)
                .notes(notes)
                .build();
    }

    public static MaintenanceTask createBrakeInspection(String vin, String notes) {
        return MaintenanceTask.builder()
                .vin(vin)
                .type(TaskType.BRAKE_INSPECTION)
                .status(TaskStatus.PENDING)
                .notes(notes)
                .build();
    }

    public static MaintenanceTask createTireService(String vin, String notes) {
        return MaintenanceTask.builder()
                .vin(vin)
                .type(TaskType.TIRE_SERVICE)
                .status(TaskStatus.PENDING)
                .notes(notes)
                .build();
    }

    public static MaintenanceTask createDiagnosticScan(String vin, String notes) {
        return MaintenanceTask.builder()
                .vin(vin)
                .type(TaskType.DIAGNOSTIC_SCAN)
                .status(TaskStatus.PENDING)
                .notes(notes)
                .build();
    }
}
