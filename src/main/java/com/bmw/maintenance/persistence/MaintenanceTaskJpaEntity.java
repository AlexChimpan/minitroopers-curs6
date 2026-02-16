package com.bmw.maintenance.persistence;

import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domain.TaskType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
}
