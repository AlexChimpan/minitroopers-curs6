package com.bmw.maintenance.persistence;

import com.bmw.maintenance.domain.ScannerType;
import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domain.TirePosition;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "maintenance_tasks")
public class MaintenanceTaskJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(length = 17, nullable = false)
    public String vin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public TaskType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public TaskStatus status;

    @Column
    public String notes;

    @Enumerated(EnumType.STRING)
    public TirePosition tirePosition;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "error_code")
    public List<String> errorCodes;

    @Enumerated(EnumType.STRING)
    public ScannerType scannerType;
}
