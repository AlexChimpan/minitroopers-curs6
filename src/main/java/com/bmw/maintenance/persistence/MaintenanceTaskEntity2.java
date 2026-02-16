package com.bmw.maintenance.persistence;

import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domain.TaskType;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "maintenance_tasks")
public class MaintenanceTaskEntity2 extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String vin;

    @Enumerated(EnumType.STRING)
    public TaskType type;

    @Enumerated(EnumType.STRING)
    public TaskStatus status;

    public String notes;
}
